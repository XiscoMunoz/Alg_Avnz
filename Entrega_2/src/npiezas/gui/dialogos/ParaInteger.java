/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npiezas.gui.dialogos;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import npiezas.Notificar;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class ParaInteger extends JPanel implements ActionListener {
    private JTextField numero;
    private Notificar prog;
    private String mensaje;
    
    public ParaInteger(Notificar n, String m, int dim) {
        prog = n;
        mensaje = m;
        this.setLayout(new FlowLayout());
        this.add(new JLabel("Nueva dimensión: "));
        numero = new JTextField(4);
        numero.setText(Integer.toString(dim));
        numero.addActionListener(this);
        this.add(numero);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == numero) {
            prog.notificar(mensaje+Integer.parseInt(numero.getText()));
        }
    }
}
