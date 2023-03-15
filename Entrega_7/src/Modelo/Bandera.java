/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class Bandera implements Serializable {
    
    private String nombrePais;
    private HashMap<String, Double> colores;
    private int points;
    
    public Bandera() {
        this.colores = new HashMap <> ();
        colores.put("Blanco",0.0);
        colores.put("Negro",0.0);
        colores.put("Rojo",0.0);
        colores.put("Verde",0.0);
        colores.put("Azul",0.0);
        colores.put("Amarillo",0.0);
        colores.put("Naranja",0.0);
         this.points = 0;
    }
    
    public Bandera(String pais){
        this.nombrePais = pais;
        this.colores = new HashMap <> ();
        colores.put("Blanco",0.0);
        colores.put("Negro",0.0);
        colores.put("Rojo",0.0);
        colores.put("Verde",0.0);
        colores.put("Azul",0.0);
        colores.put("Amarillo",0.0);
        colores.put("Naranja",0.0);
        this.points = 0;
    }
    
    public HashMap<String, Double> getPaleta(){
        return colores;
    }
    
    public String getNombrePais(){
        return this.nombrePais;
    }
    
    public void addPoint(){
        this.points++;
    }
    
    public int getPoints(){
        return this.points;
    }
    public void setPoints(int i){
    
    this.points=i;
    }
    
    public void putColorValue(String color, double value){
        this.colores.put(color, value);
    }
    
    public double getColorValue(String color){
        return this.colores.get(color);
    }

    @Override
    public String toString() {
        return "Bandera{" + "nombrePais=" + nombrePais + ", colores=" + colores + '}'+"\n";
    }
    

    
}
