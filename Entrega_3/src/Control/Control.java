/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Principal.Main;
import Principal.Error;
import Principal.Eventos;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class Control extends Thread implements Eventos {

    private Main prog;
    private int tipo;
    private long tiempo;

    public Control(Main p) {
        prog = p;
    }

    public void run() {
        this.prog.getView().notificar("Start");
        this.prog.getModel().notificar("SetNums");
        switch (tipo) {
            case 1:               
                this.prog.getView().añadirTexto("Cálculo de la multiplicacion normal");
                tiempo = System.currentTimeMillis();
                this.prog.getModel().notificar("Normal");
                tiempo = System.currentTimeMillis() - tiempo;
                this.prog.getView().añadirTexto("Tiempo en ms " + tiempo + "\n");
                break;
            case 2:
                this.prog.getView().añadirTexto("Cálculo de la multiplicacion de Karatsuba");
                tiempo = System.currentTimeMillis();
                this.prog.getModel().notificar("Karatsuba");
                tiempo = System.currentTimeMillis() - tiempo;
                this.prog.getView().añadirTexto("Tiempo en ms " + tiempo + "\n");
                break;
            case 3:
                this.prog.getView().añadirTexto("Cálculo de la multiplicacion mixta");
                tiempo = System.currentTimeMillis();
                this.prog.getModel().notificar("Mixto");
                tiempo = System.currentTimeMillis() - tiempo;
                this.prog.getView().añadirTexto("Tiempo en ms " + tiempo + "\n");
                break;
            default:
                break;
        }
        this.prog.getView().notificar("End");
    }

    private void espera(long m, int n) {
        try {
            Thread.sleep(m, n);
        } catch (Exception e) {
            Error.informaError(e);
        }
    }

    @Override
    public void notificar(String s) {
        if (s.startsWith("Normal")) {
            tipo = 1;
            this.start();
        } else if (s.startsWith("Karatsuba")) {
            tipo = 2;
            this.start();
        } else if (s.startsWith("Mixto")) {
            tipo = 3;
            this.start();
        }
    }
}
