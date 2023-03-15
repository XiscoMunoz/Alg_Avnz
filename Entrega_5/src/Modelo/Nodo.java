/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.ArrayList;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class Nodo {
    
    private String palabra;
    private ArrayList<String> palabras_propuestas;
    private int index;
    
    public Nodo(String palabra){
        this.palabra = palabra;
        this.palabras_propuestas = new ArrayList<>();
        this.index = -1;
    }
    
    public void añadir_Lista(ArrayList<String> palabras){
        palabras_propuestas.addAll(palabras);
    }
    
    public String getFirstProp() {
        return this.palabras_propuestas.get(0);
    }
    
    public void vaciar_lista(){
        palabras_propuestas.clear();
    }

    @Override
    public String toString() {
        return "Nodo{" + "palabra=" + palabra + ", palabras_propuestas=" + palabras_propuestas + ", index=" + index + '}';
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }   
    
    public int getIndex() {
        return this.index;
    }
    
    public void setIndex(int i) {
        this.index = i;
    }

    public ArrayList<String> getPalabras_propuestas() {
        return palabras_propuestas;
    }   
}
