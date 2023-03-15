/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Principal.Main;
import Principal.Error;
import Principal.Eventos;
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
    private String filePath;

    public Control(Main p) {
        prog = p;
    }

    public void run() {
        try {
            this.filePath = "";
            
            switch(this.type) { 
                case 1:
                    tiempo = System.currentTimeMillis();
                    
                    this.filePath = this.prog.getView().getPathFile();
                    this.prog.getModel().comprimir(filePath);
                    
                    tiempo = System.currentTimeMillis() - tiempo;
                    this.prog.getView().añadirTexto("Compresión finalizada");
                    this.prog.getView().añadirTexto("Tiempo en ms " + tiempo + "\n");
                    break;
                    
                case 2: 
                    this.prog.getView().añadirTexto("Descomprimiendo");
                    tiempo = System.currentTimeMillis();
                    
                    this.filePath = this.prog.getView().getPathFile();
                    this.prog.getModel().descomprimir(filePath);
                                   
                    tiempo = System.currentTimeMillis() - tiempo;
                    this.prog.getView().añadirTexto("Descompresión finalizada");
                    this.prog.getView().añadirTexto("Tiempo en ms " + tiempo + "\n");
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
        if(s.startsWith("Comprimir")) {
            this.type = 1;
            this.start();
        }  else if(s.startsWith("Descomprimir")) {
            this.type = 2;
            this.start();
        } 
    }
}
