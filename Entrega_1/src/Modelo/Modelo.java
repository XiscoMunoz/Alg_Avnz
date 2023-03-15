/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Principal.Main;
import Principal.Error;
import Principal.Eventos;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class Modelo implements Eventos {

    private Main prog;
    private int oldX, oldY, x, y;
    private int n;
    private int pixels;
    private int tipo=0;
    private int percent;

    public Modelo(Main p) {
        prog = p;
        oldX = oldY = x = y = 0;
        n = 0;
        pixels = 10;
        percent = 0;
    }

    public void setPixels(int pixels) {
        this.pixels = pixels;
    }

    public int getTipo() {
        return tipo;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setOldXY(int x, int y) {
        this.oldX = x;
        this.oldY = y;
    }

    public void setN(int n) {
        this.n = n;
    }
    
    public void setPercent(int valor) {
        percent = valor;
    }
    
    public void setTipo(int t) {
        tipo = t;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getOldX() {
        return oldX;
    }

    public int getOldY() {
        return oldY;
    }

    public int getN() {
        return n;
    }
    
    public int getPercent() {
        return percent;
    }
    
    public void resetVariables(){
        oldX = oldY = x = y = 0;
        n = 0;
    }

    private void calcularOn() {
        oldY = y;
        oldX = x;
        n++;
        
        long tiempo = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            try {
                Thread.sleep(2);
            } catch (InterruptedException ex) {
                Error.informaError(ex);
            }
        }
        y = (int)(System.currentTimeMillis()- tiempo);
        
        x = n * pixels;
        percent = Math.round( (n/32f) * 100);
    }

    private void calcularOlogn() {
        oldY = y;
        oldX = x;
        n++;
       
        long tiempo = System.currentTimeMillis();
        for (int j = n; j > 0; j = j / 2) {
            try {
                Thread.sleep(2);
            } catch (InterruptedException ex) {
                Error.informaError(ex);
            }
        }
        y = (int)(System.currentTimeMillis()- tiempo);

        x = n * pixels;
        percent = Math.round( (n/32f) * 100);
    }

    private void calcularOn2() {
        oldY = y;
        oldX = x;
        n++;
    
        long tiempo = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                try {
                    Thread.sleep(2);
                } catch (InterruptedException ex) {
                    Error.informaError(ex);
                }
            }
        }
        y = (int)(System.currentTimeMillis()- tiempo);
  
        x = n * pixels;
        percent = Math.round( (n/32f) * 100);
    }
    
    private void calcularOnlogn() {
        oldY = y;
        oldX = x;
        n++;
    
        long tiempo = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            for (int j = n; j > 0; j = j/2) {
                try {
                    Thread.sleep(2);
                } catch (InterruptedException ex) {
                    Error.informaError(ex);
                }
            }
        }
        y = (int)(System.currentTimeMillis()- tiempo);
  
        x = n * pixels;
        percent = Math.round( (n/32f) * 100);
    }
    
    @Override
    public void notificar(String s) {
        if (s.startsWith("O(n)")) {
            this.calcularOn();
        } else if (s.startsWith("O(logn)")) {
            this.calcularOlogn();
        } else if (s.startsWith("O(n2)")) {
            this.calcularOn2();
        } else if (s.startsWith("O(nlogn)")) {
            this.calcularOnlogn();
        }
    }
}
