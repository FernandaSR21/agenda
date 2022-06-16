/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Fernanda SR
 */
public class Aplicacion {
    
    private final Controlador controlador;
    private final SistemaDeArchivos sistemaDeArchivos;
    
    public Aplicacion(){
        this.sistemaDeArchivos = new SistemaDeArchivos();
        this.controlador = new Controlador(this.sistemaDeArchivos);
    }

    public static void main(String args[]){
        Aplicacion aplicacion = new Aplicacion();
    }
}
