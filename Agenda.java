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
