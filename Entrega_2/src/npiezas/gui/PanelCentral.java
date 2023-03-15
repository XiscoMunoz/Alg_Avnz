/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npiezas.gui;

import datos.Datos;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.xml.transform.OutputKeys;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class PanelCentral extends JPanel implements MouseListener {

    private Datos dat;
    private ImageIcon imgpieza;
    private String texto;

    public PanelCentral(Datos d) {
        dat = d;
        setImagenPieza(d);
        this.addMouseListener(this);
    }

    public void setImagenPieza(Datos d) {
        //cogemos la pieza
        URL imageURL = getClass().getResource(dat.getImagen());
        imgpieza = new ImageIcon(imageURL);
    }

    @Override
    public void repaint() {
        if (this.getGraphics() != null) {
            paint(this.getGraphics());
        }
    }

    @Override
    public void paint(Graphics g) {
        tablero(g);
        int dim = dat.getDimension();
        //calculamos el ancho y alto de la casilla
        int ancho = this.getWidth() / dim;
        int alto = this.getHeight() / dim;
        //colocamos las piezas
        if (dat.getSolucion() != null) {
            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {
                    if (i == dat.getxIni() && j == dat.getyIni()) {
                        g.drawImage(imgpieza.getImage(), i * ancho, j * alto, ancho, alto, this);
                    } else {
                        texto = "" + dat.getValorSolucion(i, j);
                        g.setColor(Color.ORANGE);
                        g.setFont(new Font("Tahoma", Font.BOLD, 40));
                        g.drawString(texto, (i) * ancho, (j + 1) * alto);
                    }
                }
            }
        }
    }

    public void tablero(Graphics g){
        g.setColor(new Color(255, 255, 255));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        int dim = dat.getDimension();
        //calculamos el ancho y alto de la casilla
        int ancho = this.getWidth() / dim;
        int alto = this.getHeight() / dim;
        //Pintamos el tablero
        g.setColor(new Color(0, 0, 0));
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if ((i % 2) == 0) { //la fila empieza por blanco
                    if ((j % 2) == 1) {
                        g.fillRect(j * ancho, i * alto, ancho, alto);
                    }
                } else // la fila empieza por negro
                {
                    if ((j % 2) == 0) {
                        g.fillRect(j * ancho, i * alto, ancho, alto);
                    }
                }
            }
        }
    }
    
    public void setDatos(Datos d) {
        dat = d;
    }

    @Override
    public void mouseClicked(MouseEvent me) {

        if (dat.getSolucion() == null) {
            dat.createSolucion();
        }
        dat.setValorSolucion(dat.getxIni(), dat.getyIni(), -1);
        dat.reinicioTablero();
        int width = (this.getWidth() / dat.getDimension());
        int height = (this.getHeight() / dat.getDimension());
        int x = (int) me.getX() / (width);
        int y = (int) me.getY() / (height);

        //Debe ahber un fallo por aqui sobre poner los valores
        dat.setValorSolucion(x, y, 1);
        dat.ponPieza(x, y);
        dat.setxIni(x);
        dat.setyIni(y);
        //Esto se puede retocar
        tablero(this.getGraphics());
        this.getGraphics().drawImage(imgpieza.getImage(),  x * (width),y * (height), (width), (height), this);
    }

    @Override
    public void mousePressed(MouseEvent me) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent me) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
