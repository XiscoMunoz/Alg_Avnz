/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npiezas.gui.dialogos;

import datos.Datos;
import datos.piezas.Pieza;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import npiezas.Notificar;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class ParaPieza extends JPanel implements ActionListener {

    private Notificar prog;
    private String mensaje;

    public ParaPieza(Notificar n, String m, Datos d) {
        prog = n;
        mensaje = m;
        this.setLayout(new BorderLayout());
        this.add(BorderLayout.NORTH, new JLabel("Elije pieza del juego"));
        //grupo radio buttons
        hacerRadios(d);
    }

    private void hacerRadios(Datos d) {
        //miramos cuantas piezas hay en el paquete datos.piezas
        Package pack = d.getClasePieza().getClass().getPackage();
        String paquete = pack.getName(); // Ya tenemos el package de las piezas
        URL path = getClass().getResource("/" + paquete.replaceAll("\\.", "/"));
        File dir = new File(path.getPath());
        String[] nombres = dir.list();
        String[] aux = new String[nombres.length - 1];
        int pos = 0;
        for (int i = 0; i < nombres.length; i++) {
            if (!(nombres[i].contentEquals("Pieza.class"))) {
                aux[pos++] = nombres[i].substring(0, nombres[i].indexOf(".class"));
            }
        }
        nombres = aux;
        Pieza piezas[] = new Pieza[nombres.length];
        for (int i = 0; i < piezas.length; i++) {
            try {
                Class c = Class.forName(paquete + "." + nombres[i]);
                piezas[i] = (Pieza) c.newInstance();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        // ya tenemos las piezas instanciadas hacemos los botones
        ButtonGroup group = new ButtonGroup();
        JPanel botonesrad = new JPanel();
        botonesrad.setLayout(new BoxLayout(botonesrad, BoxLayout.Y_AXIS));
        for (int i = 0; i < piezas.length; i++) {
            URL imageURL = getClass().getResource(piezas[i].getImagen());
            ImageIcon imgpieza = new ImageIcon(imageURL);
            JRadioButton rbut = new JRadioButton("");
            JLabel imbut = new JLabel(escalar(imgpieza));
            rbut.setActionCommand(piezas[i].getNombre());
            rbut.addActionListener(this);
            group.add(rbut);
            JPanel pan = new JPanel();
            pan.setLayout(new FlowLayout());
            pan.add(rbut);
            pan.add(imbut);
            botonesrad.add(pan);
            if(piezas[i].getNombre().contentEquals(d.getPieza())) {
                rbut.setSelected(true); //marcamos el boton de la pieza actual
            }
            
        }
        this.add(BorderLayout.CENTER, botonesrad);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        prog.notificar(mensaje + e.getActionCommand());
    }
    
    private ImageIcon escalar(ImageIcon im)
    {
        ImageIcon i = new ImageIcon(im.getImage()
                .getScaledInstance(32, 32, Image.SCALE_DEFAULT));
        return i;
    }
}
