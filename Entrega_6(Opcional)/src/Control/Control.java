/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Principal.Error;
import Principal.Eventos;
import Principal.Main;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class Control extends Thread implements Eventos {

    private Main prog;
    private int type;
    private long tiempo;

    public Control(Main p) {
        prog = p;
    }

    public void run() {
        try {
            switch (type) {
                case 1: 
                        tiempo = System.currentTimeMillis();
                        this.prog.getModel().mezclar(50);
                        this.prog.getView().repaint();
                        tiempo = System.currentTimeMillis() - tiempo;
                        break;
                case 2: 
                        tiempo = System.currentTimeMillis();
                        this.prog.getModel().setInicial(this.prog.getModel().camino());
                        this.prog.getView().repaint();
                        tiempo = System.currentTimeMillis() - tiempo;
                        break;
            }
        } catch (Exception ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }

            
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
        if(s.startsWith("Mezclar")) {
            this.type = 1;
            this.start();
        }  else if(s.startsWith("Resolver")) {
            this.type = 2;
            this.start();
        } 
    }
}