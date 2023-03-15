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
public class Nodo implements Comparable<Nodo> {

    byte bite;
    int freq;
    int IndiceIzq;
    int IndiceDer;
    int nombre;
    Nodo hijoIzq;
    Nodo hijoDer;

    public Nodo(byte bite, int freq) {
        this.bite = bite;
        this.freq = freq;
    }

    public Nodo() {
    }

    public Nodo(int nom, byte valo, int izq, int der) {
        nombre = nom;
        IndiceIzq = izq;
        IndiceDer = der;
        bite = valo;
    }

    public byte getBite() {
        return bite;
    }

    public void setBite(byte bite) {
        this.bite = bite;
    }

    public int getFreq() {
        return freq;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    public Nodo getHijoIzq() {
        return hijoIzq;
    }

    public void setHijoIzq(Nodo hijoIzq) {
        this.hijoIzq = hijoIzq;
    }

    public Nodo getHijoDer() {
        return hijoDer;
    }

    public void setHijoDer(Nodo hijoDer) {
        this.hijoDer = hijoDer;
    }

    @Override
    public int compareTo(Nodo t) {
        return this.freq - t.freq;
    }

    public int getIndiceIzq() {
        return IndiceIzq;
    }

    public void setIndiceIzq(int IndiceIzq) {
        this.IndiceIzq = IndiceIzq;
    }

    public int getIndiceDer() {
        return IndiceDer;
    }

    public void setIndiceDer(int IndiceDer) {
        this.IndiceDer = IndiceDer;
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return " Nodo{" + "bite=" + bite + ", freq=" + freq + ", hijoIzq=" + hijoIzq + ", hijoDer=" + hijoDer + '}';
    }

}
