/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Modelo.Modelo;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class Matriz extends JPanel{

    private Modelo dat;

    public Matriz(Modelo d) {
        dat = d;
    }

    @Override
    public void repaint() {
        if (this.getGraphics() != null) {
            paint(this.getGraphics());
        }
    }
    
    @Override
    public void paint(Graphics g) {
        int posiciones[][] = this.dat.getInicial().getEstado();
        
        for (int i = 0; i < this.dat.getDimension(); i++) {
            for (int j = 0; j < this.dat.getDimension(); j++) {
                if (posiciones[i][j] != -1) {
                    g.drawImage(this.dat.getTrozos()[posiciones[i][j]], j * this.dat.getMultY(), i * this.dat.getMultX(), null);
                } else {
                    g.drawImage(this.dat.getImgVacia(), j * this.dat.getMultY(), i * this.dat.getMultX(), null);
                }
            }
        }
    }
    
    public void setModelo(Modelo d) {
        dat = d;
    }

}