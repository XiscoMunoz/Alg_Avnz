/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import Control.Control;
import Modelo.Modelo;
import Vista.Vista;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class Main implements Eventos {

    private Modelo mod;    // Puntero al Modelo
    private Vista vis;    // Puntero a la Vista
    private Control con;  // puntero al Control

    /*
        Construcció de l'esquema MVC
     */
    private void inicio() {
        mod = new Modelo(this);
        con = null;
        vis = new Vista("Entrega_1", this);
        vis.mostrar();
    }

    public static void main(String[] args) {
        (new Main()).inicio();
    }

    /*
        Función símple de la comunicació por eventos
     */
    @Override
    public void notificar(String s) {
        
        if(s.startsWith("O(n)")){
                con = new Control(this);
                con.notificar(s); 
        } else if(s.startsWith("O(logn)")){
                con = new Control(this);
                con.notificar(s);
        } else if(s.startsWith("O(n2)")){
                con = new Control(this);
                con.notificar(s);
        } else if(s.startsWith("O(nlogn)")){
                con = new Control(this);
                con.notificar(s);
        }
        
    }

    /*
        Método public de retorno de la instancia del modelo de dades
    */
    public Modelo getModel() {
        return mod;
    }
}
