/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class Nodo {
    
   private String nombre;
   private int valor;

    public Nodo(String nombre, int valor) {
        this.nombre = nombre;
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Nodo{" + "nombre=" + nombre + ", valor=" + valor + '}';
    }

   
   
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    
   
   
   
    
}
