/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import Control.Control;
import Modelo.Modelo;
import Vista.Vista;
import java.io.IOException;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class Main implements Eventos {

    private Modelo mod;    // Puntero al Modelo
    private Vista vis;    // Puntero a la Vista
    private Control con;  // puntero al Control

    /*
        Construcción del esquema MVC
     */
    private void inicio() throws IOException {
        mod = new Modelo(this);
        con = null;
        vis = new Vista("Entrega_4", this);
        vis.mostrar();
    }

    public static void main(String[] args) throws IOException {
        (new Main()).inicio();
    }
 

    /*
        Función símple de la comunicación por eventos
     */
    @Override
    public void notificar(String s) {
        if(s.startsWith("Comprimir")) {
            con = new Control(this);
            con.notificar(s); 
        } else if(s.startsWith("Descomprimir")) {
            con = new Control(this);
            con.notificar(s); 
        } 
    }

    /*
        Método publico de retorno de la instancia del modelo de datos
    */
    public Modelo getModel() {
        return mod;
    }
    
    /*
        Método publico de retorno de la instancia de la vista
    */
    public Vista getView() {
        return vis;
    }
}
