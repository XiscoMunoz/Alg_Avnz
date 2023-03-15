/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npiezas.gui;

import datos.Datos;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import npiezas.Notificar;
import npiezas.gui.dialogos.Alerta;
import npiezas.gui.dialogos.ParaInteger;
import npiezas.gui.dialogos.ParaPieza;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class Modal extends JDialog implements ActionListener {

    private JButton ok;
    private Notificar prog;

    public Modal(Notificar p, String m, int d) {
        prog = p;
        constructA();
        this.add(new ParaInteger(prog, m, d), BorderLayout.CENTER);
        constructB();
    }

    public Modal(Notificar p, String m, Datos d) {
        prog = p;
        constructA();
        this.add(new ParaPieza(prog, m, d), BorderLayout.CENTER);
        constructB();
    }

    public Modal(Notificar p, String m) {
        prog = p;
        constructA();
        this.add(new Alerta(prog, m), BorderLayout.CENTER);
        constructB();
    }

    private void constructA() {
        this.setModal(true);
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.setLayout(new BorderLayout());
    }

    private void constructB() {
        ok = new JButton("Ok");
        ok.addActionListener(this);
        this.add(ok, BorderLayout.SOUTH);
        this.pack();
        this.setLocation(300, 300);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ok) {
            this.dispose();
        }
    }
}
