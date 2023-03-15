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
            
            switch(this.type) { 
                case 1:
                    tiempo = System.currentTimeMillis();
                    
                    this.prog.getModel().cleanCache();    
                    this.prog.getModel().detectarIdioma();
                    int nErrores = this.prog.getModel().detectarErrores();
                    this.prog.getView().markIncorrect(this.prog.getModel().getPalabras_erroneas());
                    
                    tiempo = System.currentTimeMillis() - tiempo;
                    this.prog.getView().setOutput("Tiempo transcurrido: " + tiempo  + "ms\n");
                    break;
                    
                case 2: 
                    tiempo = System.currentTimeMillis();
                    
                    this.prog.getModel().corregir();
                                   
                    tiempo = System.currentTimeMillis() - tiempo;
                    this.prog.getView().setOutput("Tiempo transcurrido: " + tiempo + "ms");
                    break;
            }
        } catch (Exception ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }

            
    }

    @Override
    public void notificar(String s) {
        if(s.startsWith("Detectar")) {
            this.type = 1;
            this.start();
        }  else if(s.startsWith("Corregir")) {
            this.type = 2;
            this.start();
        } 
    }
}
