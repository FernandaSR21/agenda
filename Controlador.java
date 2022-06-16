import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Fernanda SR
 */
public class Controlador {
    private final AgendaGUI gui;
    private final SistemaDeArchivos sistemaDeArchivos;
    private String criterioUltimaBusqueda;
    private int ultimoResultadoBusqueda;
    
    public Controlador(SistemaDeArchivos sistemaDeArchivos){
        this.sistemaDeArchivos = sistemaDeArchivos;
        this.gui = new AgendaGUI(this);
        this.gui.setAgenda(new Agenda());
        this.gui.setVisible(true);
    }
    
    public void agregar(){
        String[] rotulos = {"Nombre", "Apellido", "Direccion", "Ciudad", "Estado", "CP", "Telefono"};
        
        String[] informacion = Formulario.mostrarFormulario(this.gui, rotulos, "Ingrese datos");
        
        if(informacion != null){
            String nombreCompleto = Persona.nombreCompleto(informacion[0], informacion[1]);
            String[] informacionPersona = this.gui.getAgenda().obtenerInformacion(nombreCompleto);
            if(informacionPersona != null)
                this.gui.mostrarError("Contacto duplicado");
            else
                this.gui.getAgenda().agregarPersona(informacion[0], 
                        informacion[1], informacion[2], informacion[3], 
                        informacion[4], informacion[5], informacion[6]);
        }
    }
    
    public void editar(String nombrePersona){
        if(nombrePersona == null){
            this.gui.mostrarError("Debe seleccionar un contacto de la lista");
            return;
        }
        
        String[] valoresIniciales = this.gui.getAgenda().obtenerInformacion(nombrePersona);
        if(valoresIniciales == null){
            this.gui.mostrarError("Contacto no encontrado");
            return;
        }
        
        String[] rotulos = {"Direccion", "Ciudad", "Estado", "CP", "Telefono"};
        
        String[] informacionAct = Formulario.mostrarFormulario(this.gui, rotulos, valoresIniciales, "Editar: "+nombrePersona);
        
        if(informacionAct != null){
            this.gui.getAgenda().actualizarPersona(
                    nombrePersona, 
                    informacionAct[0], 
                    informacionAct[1], 
                    informacionAct[2], 
                    informacionAct[3], 
                    informacionAct[4]);
        }
    }
    
    public void eliminar(String nombrePersona){
        
        if(nombrePersona == null){
            this.gui.mostrarError("Debe seleccionar un contacto de la lista");
            return;
        }
        
        if(JOptionPane.showConfirmDialog(this.gui, "¿Esta seguro de querer eliminar a "+nombrePersona+"?",
                "Confirmar eliminación",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            this.gui.getAgenda().eliminarPersona(nombrePersona);
        }
    }
    
    public void llamarOrdenarPorNombre(){
        this.gui.getAgenda().ordenarPorNombre();
    }
    
    public void llamarOrdenarPorCP(){
        this.gui.getAgenda().ordenarPorCP();
    }
    
    public void buscar(int indice){
        this.criterioUltimaBusqueda = JOptionPane.showInputDialog(this.gui, "Buscar: ");
        if(this.criterioUltimaBusqueda != null){
            this.ultimoResultadoBusqueda = this.gui.getAgenda().buscar(criterioUltimaBusqueda, indice);
            this.gui.busquedaEncontrada(this.ultimoResultadoBusqueda);
        }
    }
    
    public void buscarDeNuevo(){
        if(this.ultimoResultadoBusqueda < 0){
            this.buscar(-1);
        }else{
            this.ultimoResultadoBusqueda = this.gui.getAgenda().buscar(this.criterioUltimaBusqueda, this.ultimoResultadoBusqueda+1);
            this.gui.busquedaEncontrada(this.ultimoResultadoBusqueda);
        }
    }
    
    /**
     * Opciones de menu
     */
    
    public void nuevo(){
        if(this.gui.getAgenda().getHayCambios()){
            if(this.guardarCambios() != true) return;
        }
        this.gui.setAgenda(new Agenda());
    }
    
    public void abrir(){
        if(this.gui.getAgenda().getHayCambios()){
            if(this.guardarCambios() != true) return;
        }
        JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
        if(chooser.showOpenDialog(this.gui) == JFileChooser.APPROVE_OPTION){
            File archivo = chooser.getSelectedFile();
            this.gui.setAgenda(this.sistemaDeArchivos.leerArchivo(archivo));
        }
    }
    
    public boolean guardar(){
        File archivo = this.gui.getAgenda().getArchivo();
        if(archivo == null) {
            return this.guardarComo();
        }
        else{
            this.sistemaDeArchivos.guardarArchivo(this.gui.getAgenda(), archivo);
            return true;
        }
    }
    
    public boolean guardarComo(){
        JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
        if(chooser.showSaveDialog(this.gui) == JFileChooser.APPROVE_OPTION){
            File archivo = chooser.getSelectedFile();
            this.sistemaDeArchivos.guardarArchivo(this.gui.getAgenda(), archivo);
            return true;
        }else{
            return false;
        }
    }
    
    public void imprimir(){
        String directorio = this.sistemaDeArchivos.getUbicacionDefault();
        JFileChooser chooser = new JFileChooser(directorio);
        if(chooser.showDialog(this.gui, "Imprimir") == JFileChooser.APPROVE_OPTION){
            try (PrintWriter impresora = new PrintWriter(new FileWriter(chooser.getSelectedFile()))) {
                this.gui.getAgenda().imprimir(impresora);
                if(impresora.checkError()){
                    JOptionPane.showInputDialog("Error de impresion");
                }
            }
            catch (IOException ex) {
                JOptionPane.showInputDialog(ex.getMessage());
            }
        }
    }
    
    public void salir(){
        if(this.gui.getAgenda().getHayCambios()){
            if(this.guardarCambios() == false){
                throw new IllegalStateException();
            }
        }
        System.exit(0);
    }
    
    public boolean guardarCambios(){
        int respuesta = JOptionPane.showConfirmDialog(this.gui, "¿Desea guardar los cambios?", "Guardar",JOptionPane.YES_NO_CANCEL_OPTION);
        switch(respuesta){
            case JOptionPane.NO_OPTION: return true;
            case JOptionPane.YES_OPTION: return this.guardar();
            case JOptionPane.CANCEL_OPTION:
            default: return false;
        }
    }
    
    
    
}
