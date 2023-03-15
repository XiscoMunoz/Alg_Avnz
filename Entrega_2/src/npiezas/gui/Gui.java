/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npiezas.gui;

import datos.Datos;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import npiezas.Notificar;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class Gui implements ActionListener {

    private JFrame vent;
    private JToolBar barra;
    private JPanel contenedor;
    private Datos dat;
    private PanelCentral central;
    private Notificar prog;

    public Gui(String s, int x, int y, Datos d, Notificar p) {
        dat = d;
        prog = p;
        vent = new JFrame(s);
        vent.setPreferredSize(new Dimension(x, y));
        crear();
    }

    public void crear() {
        contenedor = new JPanel();
        contenedor.setLayout(new BorderLayout());
        barra = new JToolBar();
        contenedor.add(BorderLayout.PAGE_START, barra);
        central = new PanelCentral(dat);
        contenedor.add(BorderLayout.CENTER, central);
        vent.add(contenedor);
    }

    public void setImagenPieza(Datos d) {
        central.setImagenPieza(d);
    }
    
    public void ponOpcion(String s1, String s2, String s3, String s4) {
        JButton bot = null;
        bot = makeNavigationButton(s1, s2, s3, s4);
        barra.add(bot);
    }

    public void visualizar() {
        vent.pack();
        vent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vent.setVisible(true);
    }

    protected JButton makeNavigationButton(String imageName,
            String actionCommand,
            String toolTipText,
            String altText) {
        String imgLocation = "/" + imageName;
        JButton button = new JButton();
        button.setActionCommand(actionCommand);
        button.setToolTipText(toolTipText);
        button.addActionListener(this);
        URL imageURL = getClass().getResource(imgLocation);
        if (imageURL != null) {                      //image found
            button.setIcon(new ImageIcon(imageURL, altText));
        } else {                                     //no image found
            button.setText(altText);
            System.err.println("Resource not found: " + imgLocation);
        }
        button.setText(altText);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String aux = e.toString();
        aux = aux.substring(aux.indexOf(",cmd=")+5,aux.indexOf(",when="));
        prog.notificar(aux);
    }
    
    public void repintar() {
        central.repaint();
    }
    
    public void repaint() {
        if (vent != null) {
            vent.paint(vent.getGraphics());
        }
    }
    
    public void setDatos(Datos d) {
        dat = d;
        central.setDatos(d);
    }
}
