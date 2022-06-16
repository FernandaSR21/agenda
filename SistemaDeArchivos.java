import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Fernanda SR
 */
public class SistemaDeArchivos {
    private String ubicacionDefault;
    
    
    public Agenda leerArchivo(File archivo){
        ObjectInputStream stream = null;
        Agenda resultado = null;
        try{
            stream = new ObjectInputStream(new FileInputStream(archivo));
            resultado = (Agenda) stream.readObject();
            resultado.setArchivo(archivo);
            resultado.setNoHayCambios();
            this.ubicacionDefault = archivo.getParent();
        }catch(IOException | ClassNotFoundException e){
        }
        return resultado;
    }
    
    public void guardarArchivo(Agenda agenda, File archivo){
        try {
            ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(archivo));
            stream.writeObject(agenda);
            agenda.setArchivo(archivo);
            agenda.setNoHayCambios();
            this.ubicacionDefault = archivo.getParent();
        } catch (IOException ex) {
            
        }
    }
    
    public String getUbicacionDefault(){
        return this.ubicacionDefault;
    }
    
}
