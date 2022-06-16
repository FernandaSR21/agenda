Agenda
/*
*este metodo se encarga de iniciar la ejecucion del programa
* @author Fernanda SR
*/
import java.io.File;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Collections;
import java.util.Observable;
import java.util.Vector;
import javax.swing.JOptionPane;


public class Agenda extends Observable implements Serializable{
    
    private File archivo;
    private boolean hayCambios;
    private final Vector<Persona> coleccion;
    
    public Agenda(){
        this.coleccion = new Vector<Persona>();
        this.archivo = null;
        //datosPrueba();
        this.hayCambios = false;
    }
    /*
*este metodo se encarga de pedir el nombre completo
* @param getnombre es una cadena de texto con el nombre a usar 
* @return muestra el resultado
*/
    public String[] getNombres(){
        String resultado[] = new String[this.coleccion.size()];
        for(int i = 0; i < this.coleccion.size(); i++)
            resultado[i] = this.coleccion.elementAt(i).getNombreCompleto();
        return resultado;
    }
    
    public void agregarPersona(String nombre, String apellido, 
            String direccion, String ciudad, String estado, String cp, 
            String telefono){
       Persona p = new Persona(nombre, apellido, direccion, ciudad, estado, cp, telefono);
       this.coleccion.addElement(p);
       this.hayCambios = true;
       //notificar al observador
       setChanged();
       notifyObservers();
    }
    
    public String[] obtenerInformacion(String nombre){
        int indice = this.encontrarIndice(nombre);
        if(indice >= 0){
            Persona persona = this.coleccion.elementAt(indice);
            String[] resultado = {
                persona.getDireccion(),
                persona.getCiudad(),
                persona.getEstado(),
                persona.getCodigoPostal(),
                persona.getTelefono()
            };
            return resultado;
        }
        else return null;
    }
    
    public void actualizarPersona(String nombre, 
            String direccion, String ciudad, String estado, String cp, 
            String telefono){
        try{
            int indice = this.encontrarIndice(nombre);
            if(indice>=0){
                this.coleccion.elementAt(indice).actualizar(direccion, ciudad, estado, cp, telefono);
                this.hayCambios = true;
                //notificar al observador
            }
        }catch(IllegalArgumentException e){
            JOptionPane.showInputDialog("No existe la persona");
        }
        setChanged();
       notifyObservers();
    }
    
    public void eliminarPersona(String nombre){
        int indice = encontrarIndice(nombre);
        if(indice >= 0){
            this.coleccion.removeElementAt(indice);
            this.hayCambios = true;
            //notificar al observador
            setChanged();
            notifyObservers();
        }
    }
    
    public void ordenarPorNombre(){
        Collections.sort(this.coleccion, new Persona.CompararPorNombre());
        this.hayCambios = true;
        setChanged();
        notifyObservers();
    }
    
    public void ordenarPorCP(){
        Collections.sort(this.coleccion, new Persona.CompararPorCP());
        this.hayCambios = true;
        setChanged();
        notifyObservers();
    }
    
    public int buscar(String criterio, int indiceInicial){
        for(int i = indiceInicial; i < this.coleccion.size(); i++){
          if(this.coleccion.elementAt(i).contiene(criterio)){
              return i;
          }
        }
        return -1;
    }
    
    public File getArchivo(){
        return this.archivo;
    }
    
    public void setArchivo(File archivo){
        this.archivo = archivo;
    }
    
    public String getTitulo(){
        if(this.archivo == null)
            return "Sin titulo";
        return this.archivo.getName();
    }
    
    public void imprimir(PrintWriter impresora){
        for(int i = 0; i < this.coleccion.size(); i++){
            Persona persona = this.coleccion.elementAt(i);
            impresora.println(persona.getNombreCompleto());
            impresora.println(persona.getDireccion());
            impresora.println(persona.getCiudad()+", "+persona.getEstado()+", "
            +persona.getCodigoPostal());
            impresora.println();
        }
    }
    
    public boolean getHayCambios(){
        return this.hayCambios;
    }
    
    public void setNoHayCambios(){
        this.hayCambios = false;
    }
    
    public int encontrarIndice(String nombre){
        for(int i = 0; i < this.coleccion.size(); i++){
            Persona persona = this.coleccion.elementAt(i);
            if(persona.getNombreCompleto().equals(nombre))
                return i;
        }
        return -1;
    }
     
    public void datosPrueba(){
        agregarPersona("Jesse", "Ontiveros Paredes","","","","18934889", "43461216");
        agregarPersona("Jacob Enrique", "Martinez Velarde","","","","18935346","C3B65F17");
        agregarPersona("Jose Alonso", "Aramburo Ortiz", "", "", "","15882055", "492F44B8");
        agregarPersona("Abel Arturo","Nuñez Velarde","","","","18934986","13A4B619");
        agregarPersona("Jesus Antonio","Hernandez Mejia","","","","15886042", "63FD8819");
        
/*
        (15885461, 'Osuna Terriquez Jesus Miguel', '43E28CC7', ''),
(18934854, 'Ramos Jimenez Pamela', 'E3378C19', ''),
(18934978, 'Velazquez Zamora Jose Luis', 'E3D54815', ''),
(15858200, 'Abrego Rios Daniela', '11369F2E', ''),
(17937132, 'Licona Jimenez Gabriel', '83CADB16', ''),
(18934730, 'Lopez Arangure Erika Maraly', 'F3E2A816', ''),
(18935397, 'Lopez Ibarra Jesus Antonio', '7334CC16', ''),
(18934749, 'Lopez Lizarraga Cinthya Lizeth', '83B39E19', ''),
(18935036, 'Lopez Zatarain Hugo Alonso', '13F19416', ''),
(18935370, 'Mancinas Gaxiola Silvia Berenice', '234AB117', ''),
(18935461, 'Medina Rocha Carlos Ernesto', 'B307A116', ''),
(15934241, 'SAnchez Chicas Edgar Uriel', '738B8E16', ''),
(18935168, 'Gonzalez Cervantes Luis Eduardo', '63C2C116', '');
*/
 }
 
 Agenda.GUI
 import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Fernanda SR
 */
public class AgendaGUI extends JFrame implements Observer{
    
    private DefaultListModel contenidoListaNombres;
    private JList listaPersonas;
    private JButton btnAgregar, btnEditar, btnEliminar;
    private JMenuItem menuItemNuevo, menuItemAbrir, menuItemGuardar, menuItemGuardarComo, menuItemImprimir, menuItemSalir, menuItemOrdenarNombre, menuItemOrdenarCP, menuItemBuscar, menuItemBuscarDeNuevo;
    private JMenu menuArchivo;
    private JMenu menuOrdenar;
    private JMenu menuBuscar;
    private JMenuBar menuBar;
    
    private final Controlador controlador;
    private Agenda agenda;    


    public AgendaGUI(Controlador controlador){
        initComponents();
        centrarVentana();
        this.controlador = controlador;
    }
    
    private void centrarVentana(){
        setLocationRelativeTo(null);
    }
    
    private void initComponents(){
        this.contenidoListaNombres = new DefaultListModel();
        this.listaPersonas = new JList(this.contenidoListaNombres);
        JScrollPane panelLista = new JScrollPane(this.listaPersonas);
        panelLista.setViewportView(this.listaPersonas);
        this.listaPersonas.setVisibleRowCount(10);
        
        JPanel panelBotones = new JPanel();
        this.btnAgregar = new JButton("Agregar");
        this.btnAgregar.setPreferredSize(null);
        
        this.btnEditar = new JButton("Editar");
        this.btnEditar.setPreferredSize(null);
        
        this.btnEliminar = new JButton("Eliminar");
        this.btnEliminar.setPreferredSize(null);
        
        panelBotones.add(this.btnAgregar);
        panelBotones.add(this.btnEditar);
        panelBotones.add(this.btnEliminar);
        
        this.menuBar = new JMenuBar();
        setJMenuBar(this.menuBar);
        
        this.menuArchivo = new JMenu("Archivo");
        
        this.menuItemNuevo = new JMenuItem("Nuevo");
        this.menuItemNuevo.setAccelerator(keyStroke(KeyEvent.VK_N,0));
        
        this.menuItemAbrir = new JMenuItem("Abrir");
        this.menuItemAbrir.setAccelerator(keyStroke(KeyEvent.VK_A, 0));
        
        this.menuItemGuardar = new JMenuItem("Guardar");
        this.menuItemGuardar.setAccelerator(keyStroke(KeyEvent.VK_G, 0));
        
        this.menuItemGuardarComo = new JMenuItem("Guardar como...");
        this.menuItemGuardarComo.setAccelerator(keyStroke(KeyEvent.VK_U, 0));
        
        this.menuItemImprimir = new JMenuItem("Imprimir");
        this.menuItemImprimir.setAccelerator(keyStroke(KeyEvent.VK_I, 0));
        
        this.menuItemSalir = new JMenuItem("Salir");
        this.menuItemSalir.setAccelerator(keyStroke(KeyEvent.VK_S, 0));
        
        this.menuArchivo.add(this.menuItemNuevo);
        this.menuArchivo.add(this.menuItemAbrir);
        this.menuArchivo.addSeparator();
        
        this.menuArchivo.add(this.menuItemGuardar);
        this.menuArchivo.add(this.menuItemGuardarComo);
        this.menuArchivo.addSeparator();
        
        this.menuArchivo.add(this.menuItemImprimir);
        this.menuArchivo.add(this.menuItemSalir);
        this.menuArchivo.addSeparator();
        this.menuBar.add(this.menuArchivo);
        
        this.menuOrdenar = new JMenu("Ordenar");
        this.menuItemOrdenarNombre = new JMenuItem("Ordenar por nombre");
        this.menuItemOrdenarNombre.setAccelerator(keyStroke(KeyEvent.VK_M, 0));
        
        this.menuItemOrdenarCP = new JMenuItem("Ordenar por CP");
        this.menuItemOrdenarCP.setAccelerator(keyStroke(KeyEvent.VK_P, 0));

        this.menuOrdenar.add(this.menuItemOrdenarNombre);
        this.menuOrdenar.add(this.menuItemOrdenarCP);
        this.menuBar.add(this.menuOrdenar);
        
        this.menuBuscar = new JMenu("Buscar");
        this.menuItemBuscar = new JMenuItem("Buscar");
        this.menuItemBuscar.setAccelerator(keyStroke(KeyEvent.VK_B, 0));
        
        this.menuItemBuscarDeNuevo = new JMenuItem("Buscar de nuevo");
        this.menuItemBuscarDeNuevo.setAccelerator(keyStroke(KeyEvent.VK_D, 0));

        this.menuBuscar.add(this.menuItemBuscar);
        this.menuBuscar.add(this.menuItemBuscarDeNuevo);
        this.menuBar.add(this.menuBuscar);
        
        configurarBotonesInferiores();
        configurarOpcionesMenu();
        
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        layoutAgenda(panelLista);
        
        pack();
    }
    
    /**
     * Funciones para botones inferiores
     */
    
    private void agregarContacto(){
         this.controlador.agregar();
    }
    
    private void editarContacto(){
        this.controlador.editar((String) this.listaPersonas.getSelectedValue());
    }
    
    private void eliminarContacto(){
        this.controlador.eliminar((String) this.listaPersonas.getSelectedValue());
    }
    
    private void ordernarPorNombre(){
        this.controlador.llamarOrdenarPorNombre();
    }
    
    private void ordernarPorCP(){
        this.controlador.llamarOrdenarPorCP();
    }
    
    private void buscar(){
        this.controlador.buscar(listaPersonas.getSelectedIndex()+1);
    }
    
    private void buscarDeNuevo(){
        this.controlador.buscarDeNuevo();
    }
    
    private void configurarOpcionesMenu(){
        menuItemNuevo.addActionListener((ActionEvent evt) -> {
            this.controlador.nuevo();
        });
        
        menuItemAbrir.addActionListener((ActionEvent evt) -> {
            this.controlador.abrir();
        });
        
        menuItemGuardar.addActionListener((ActionEvent evt) -> {
            this.controlador.guardar();
        });
        
        menuItemGuardarComo.addActionListener((ActionEvent evt) -> {
            this.controlador.guardarComo();
        });
        
        menuItemImprimir.addActionListener((ActionEvent evt) -> {
            this.controlador.imprimir();
        });
        
        menuItemSalir.addActionListener((ActionEvent evt) -> {
            this.controlador.salir();
        });
        
        menuItemOrdenarNombre.addActionListener((ActionEvent evt) -> {
            this.ordernarPorNombre();
        });
        
        menuItemOrdenarCP.addActionListener((ActionEvent evt) -> {
            this.ordernarPorCP();
        });
        
        menuItemBuscar.addActionListener((ActionEvent evt) -> { 
            this.buscar();
        });
        
        menuItemBuscarDeNuevo.addActionListener((ActionEvent evt) -> { 
            this.buscarDeNuevo();
        });
    }
    
    private void configurarBotonesInferiores(){
        
        btnAgregar.addActionListener((ActionEvent evt) -> {
           agregarContacto();
        });

        btnEditar.addActionListener((ActionEvent evt) -> {
            editarContacto();
        });

        btnEliminar.addActionListener((ActionEvent evt) -> {
            eliminarContacto();
        });
    }
    
    private void layoutAgenda(JScrollPane panelLista){
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(panelLista)
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 11, Short.MAX_VALUE)
                        .addComponent(btnAgregar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEditar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar)
                        .addGap(14, 14, 14)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelLista, GroupLayout.PREFERRED_SIZE, 232, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar))
                .addContainerGap())
        );
    }
    
    private KeyStroke keyStroke(int key, int modifiers){
        return KeyStroke.getKeyStroke(key, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() | modifiers);
    }
    
    @Override
    public void update(Observable observable, Object arg) {
        if(observable == this.agenda){
            int indiceActual = this.listaPersonas.getSelectedIndex();
            this.contenidoListaNombres.removeAllElements();
            String[] nombres = this.agenda.getNombres();
            for (String nombre : nombres) {
                this.contenidoListaNombres.addElement(nombre);
            }
            if(indiceActual >= 0)
                this.listaPersonas.ensureIndexIsVisible(indiceActual);
            else
                this.listaPersonas.ensureIndexIsVisible(this.contenidoListaNombres.getSize()-1);
            
            /*SwingUtilities.invokeLater(() -> {
                actualizar();
            });*/
            
            this.listaPersonas.repaint();
            
            setTitle(this.agenda.getTitulo());
        }
    }
    
    private void actualizar(){
         this.listaPersonas.repaint();
    }

    public Agenda getAgenda(){
        return this.agenda;
    }
    
    public void setAgenda(Agenda agenda){
        this.agenda = agenda;
        this.agenda.addObserver(this);
        update(this.agenda, null);
    }
    
    public void mostrarError(String mensaje){
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public void busquedaEncontrada(int indice){
        if(indice >= 0){
            listaPersonas.setSelectedIndex(indice);
            listaPersonas.ensureIndexIsVisible(indice);
        }else{
            listaPersonas.clearSelection();
            mostrarError("No se encontro registro");
        }
    }
 
}

