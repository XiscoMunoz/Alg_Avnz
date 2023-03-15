/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npiezas;

import datos.Datos;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class Juego extends Thread {

    private Datos dat;
    private Notificar prog;
    private int soluciones;
    private boolean continuar = true;

    public Juego(Datos d, Notificar n) {
        dat = d;
        prog = n;
        
    }

    @Override
    public void run() {
        soluciones = 0;
        long tiempo = System.nanoTime();
        if (dat.getSolucion() == null) {
            dat.createSolucion();
            dat.ponPieza(0, 0);
            dat.setValorSolucion(0, 0, 1);
        }
        ponerPieza(1, dat.getxIni(), dat.getyIni());
        if (soluciones == 0) {
            prog.notificar("ponalerta:Solución no encontrada");
        } else {
            tiempo = System.nanoTime() - tiempo;
            tiempo = tiempo / 1000000;
            String aux = "He tardado: " + tiempo + " milisegundos";
            prog.notificar("ponalerta:" + aux);
        }
    }

    private void ponerPieza(int n, int x, int y) {
        
        if (n == dat.getDimension() * dat.getDimension()) {
            prog.notificar("repintar");
            soluciones++;

            continuar = false;
        }      
        
        for (int i = 0; i < dat.getNumMovs() && continuar; i++) {
            int newX = x + dat.getMovX(i);
            int newY = y + dat.getMovY(i);
            
            if (newX >= 0 && newY >= 0) {
                if (newX < dat.getDimension() && newY < dat.getDimension()) {
                    if (!dat.hayPieza(newX, newY))
                    {
                        dat.setValorSolucion(newX, newY, n + 1);
                        dat.ponPieza(newX, newY);
                        ponerPieza(n + 1, newX, newY);
                        dat.quitaPieza(newX, newY);
                        dat.setValorSolucion(newX, newY, -1);
                    }
                }
            }
        }

    }

    private void esperar() {
        try {
            Thread.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDatos(Datos d) {
        dat = d;
    }
}
