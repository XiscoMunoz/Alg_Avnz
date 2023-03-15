/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Principal.Main;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class Nodo implements Comparable<Nodo> {

    private Main prog;
    private Nodo anterior;
    private int valor;//numero de casillas que estan mal
    private int[][] estado;
    private int iteracion;//numero de iteracion
    private int posiX;
    private int posiY;

    public Nodo(Main prog) {
        this.prog = prog;
    }

    public Nodo(Main prog, Nodo anterior, int[][] estado, int iteracion, int posiX, int posiY) {
        this.prog = prog;
        this.anterior = anterior;
        this.estado = new int[this.prog.getModel().getDimension()][this.prog.getModel().getDimension()];
        for (int i = 0; i < this.prog.getModel().getDimension(); i++) {
            for (int j = 0; j < this.prog.getModel().getDimension(); j++) {
                this.estado[i][j] = estado[i][j];
            }
        }
        this.iteracion = iteracion;
        this.posiX = posiX;
        this.posiY = posiY;

    }

    @Override
    public String toString() {
        String aux = "";

        for (int i = 0; i < estado.length; i++) {
            for (int j = 0; j < estado.length; j++) {
                aux += estado[i][j] + " ";
            }
            aux += "\n";
        }

        return "Nodo : POSICION DE X: " + getPosiX() + "POSICION DE Y: " + getPosiY() + " \n" + aux;
    }

    public int valormatriz(int i, int j) {
        return estado[i][j];
    }

    public int getPosiX() {
        return posiX;
    }

    public void setPosiX(int posiX) {
        this.posiX = posiX;
    }

    public int getPosiY() {
        return posiY;
    }

    public void setPosiY(int posiY) {
        this.posiY = posiY;
    }

    public int getIteracion() {
        return iteracion;
    }

    public void setIteracion(int iteracion) {
        this.iteracion = iteracion;
    }

    public Nodo getAnterior() {
        return anterior;
    }

    public void setAnterior(Nodo anterior) {
        this.anterior = anterior;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int[][] getEstado() {
        return estado;
    }

    public void setEstado(int[][] estado) {
        this.estado = estado;
    }

    @Override
    public int compareTo(Nodo t) {
        int aux = (this.getValor() + this.getIteracion()) - (t.getValor() + t.getIteracion());
        if (aux == 0) {
            aux = this.getValor() - t.getValor();

        }
        return aux;
    }

}