/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import Principal.Error;
import Modelo.Modelo;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class PanelDibujo extends JPanel {

    private int w;
    private int h;
    private Modelo mod;
    private Vista vis;
    protected final int FPS = 24;  // 24 frames por segundo
    private final ProcesoDibujo procpin;
    private BufferedImage bima;

    public PanelDibujo(int x, int y, Modelo m, Vista v) {
        w = x;
        h = y;
        mod = m;
        vis = v;
        bima = null;
        this.setPreferredSize(new Dimension(w, h));
        
                
        procpin = new ProcesoDibujo(this);
        //el 30 es por las iteraciones, si las iteraciones son fijas pues lo podemos dejar asi pero si las introduce el ususario habria que cambiar
        procpin.start();
    }

    public void repaint() {
        if (this.getGraphics() != null) {
            paint(this.getGraphics());
        }
    }

    public void paint(Graphics gr) {
        Graphics2D aux = (Graphics2D) gr;
        aux.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        mod.setPixels((int) (getWidth() - 40) / 31);

        aux.setStroke(new BasicStroke(2.0f));
        
        aux.setColor(Color.BLACK);
       
        //Pintado de la gráfica
        aux.drawLine(20, 20, 20, getHeight() - 20);
        aux.drawLine(20, getHeight() -20, getWidth() - 20, getHeight() - 20);
        aux.drawLine(20, 20, 10, 30);
        aux.drawLine(20, 20, 30, 30);
        aux.drawLine(getWidth() - 30, getHeight() - 10, getWidth() - 20, getHeight() - 20);
        aux.drawLine(getWidth() - 30, getHeight() - 30, getWidth() - 20, getHeight() - 20);

        switch (mod.getTipo()) {
            case 1:
                aux.setColor(Color.BLUE);
                break;
            case 2:
                aux.setColor(Color.RED);
                break;
            case 3:
                aux.setColor(Color.green);
                break;
            case 4:
                aux.setColor(Color.PINK);
                break;
        }
        
        aux.drawLine(mod.getOldX() + 20, ( getHeight() - mod.getOldY() - 20), mod.getX() + 20, ( getHeight() - mod.getY() - 20));
        vis.actualizarBarra(mod.getPercent());
    }
}

class ProcesoDibujo extends Thread {

    private PanelDibujo pan;

    public ProcesoDibujo(PanelDibujo pd) {
        pan = pd;
    }

    public void run() {
        long temps = System.nanoTime();
        long tram = 1000000000L / pan.FPS;
        while (true) {
            if ((System.nanoTime() - temps) > tram) {
                pan.repaint();
                temps = System.nanoTime();
                espera((long) (tram / 2000000));
            }
        }
    }

    private void espera(long t) {
        try {
            Thread.sleep(t);
        } catch (Exception e) {
            Error.informaError(e);
        }
    }
}
