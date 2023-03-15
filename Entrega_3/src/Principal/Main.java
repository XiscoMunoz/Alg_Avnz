/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import Control.Control;
import Modelo.Modelo;
import Vista.Vista;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
        Construcció de l'esquema MVC
     */
    private void inicio() throws IOException {
        mod = new Modelo(this);
        con = null;
        vis = new Vista("Entrega_3", this);
        calculoPuntoCorte();
        vis.mostrar();
    }

    public static void main(String[] args) throws IOException {
        (new Main()).inicio();
    }
    
    public void calculoPuntoCorte() throws IOException {
        File archivo = new File("PuntoCorte.txt");

        if (!archivo.exists()) {

            FileWriter fw = new FileWriter(archivo);
            BufferedWriter bw = new BufferedWriter(fw);
            this.mod.calculoCorte();
            bw.write(Integer.toString(this.mod.getNumeroCorte()));

            bw.close();
            fw.close();
            
        } else {
            
            FileReader fr = new FileReader(archivo);
            BufferedReader br = new BufferedReader(fr);
            this.mod.setNumeroCorte(Integer.parseInt(br.readLine()));
            br.close();
            fr.close();
            
        }
    }

    /*
        Función símple de la comunicació por eventos
     */
    @Override
    public void notificar(String s) {
        
        if(s.startsWith("Normal")){
                con = new Control(this);
                con.notificar(s); 
        } else if(s.startsWith("Karatsuba")){
                con = new Control(this);
                con.notificar(s);
        } else if(s.startsWith("Mixto")){
                con = new Control(this);
                con.notificar(s);
        } else if(s.startsWith("SetRandom:")) {
            String aux = s.substring(s.indexOf(":") + 1);

            this.vis.setRandomNum1(Integer.parseInt(aux.split(",")[0]));
            this.vis.setRandomNum2(Integer.parseInt(aux.split(",")[1]));
        }
        
    }

    /*
        Método public de retorno de la instancia del modelo de dades
    */
    public Modelo getModel() {
        return mod;
    }
    
    /*
        Método public de retorno de la instancia de la vista
    */
    public Vista getView() {
        return vis;
    }
}
