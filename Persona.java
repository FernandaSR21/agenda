/**
 * Persona.java
 * 
 * @author Fernanda SR
 */
import java.io.Serializable;
import java.util.Comparator;


/**
 * Objeto que mantiene la informacion de un individuo en la agenda
 * 
 */
public class Persona implements Serializable{
    private String nombre;
    private String apellido;
    private String direccion;
    private String ciudad;
    private String estado;
    private String codigo_postal;
    private String telefono;
    
    /**
     * Constructor 
     * @param nombre - Nombre del contacto
     * @param apellido - Apellido del contacto
     * @param direccion - Direccion del contatco
     * @param ciudad - Ciudad del contacto
     * @param estado - Estado del contacto
     * @param codigo_postal - Codigo postal del contacto
     * @param telefono  - Telefono del contacto
     */
    
    public Persona(String nombre, String apellido, String direccion, 
            String ciudad, String estado, String codigo_postal, 
            String telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.estado = estado;
        this.codigo_postal = codigo_postal;
        this.telefono = telefono;
    }
    
    /**
     * Accesor para el nombre del contacto
     * @return nombre del contacto
     */
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCodigoPostal() {
        return codigo_postal;
    }

    public void setCodigoPostal(String codigo_postal) {
        this.codigo_postal = codigo_postal;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString(){
        return this.nombre + " " + this.apellido;
    }
    
    public void actualizar(String direccion, String ciudad, String estado, String codigo_postal, String telefono){
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.estado = estado;
        this.codigo_postal = codigo_postal;
        this.telefono = telefono;
    }
    
    public String getNombreCompleto(){
        //return this.nombre+" "+this.apellido;
        return nombreCompleto(this.nombre, this.apellido);
    }
    
    public static String nombreCompleto(String nombre, String apellido){
        return apellido +", "+nombre;
    }
    
    public boolean contiene(String criterio){
        return this.nombre.contains(criterio)||
               this.apellido.contains(criterio)||
               this.direccion.contains(criterio)||
               this.ciudad.contains(criterio)||
               this.estado.contains(criterio)||
               this.codigo_postal.contains(criterio)||
               this.telefono.contains(criterio);
    }
    
    public static class CompararPorNombre implements Comparator<Persona>{

        @Override
        public int compare(Persona persona1, Persona persona2) {
            int resultado = persona1.apellido.compareTo(persona2.apellido);
            if(resultado == 0)
                return persona1.nombre.compareTo(persona2.nombre);
            else
                return resultado;
        }
    }
    
    public static class CompararPorCP implements Comparator<Persona>{

        @Override
        public int compare(Persona persona1, Persona persona2) {
            int resultado = persona1.codigo_postal.compareTo(persona2.codigo_postal);
            if(resultado == 0)
                return new CompararPorNombre().compare(persona1, persona2);
            else
                return resultado;
        }
    }
}
