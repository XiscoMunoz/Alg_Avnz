/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Principal.Main;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class DimensionForm extends JFrame {
    
    private Main prog;
    
    public DimensionForm(Main prog) {
        super("Dimension form");
        this.prog = prog;
        initComponents();
    }   
    
    public void initComponents() {
        this.getContentPane().setLayout(new FlowLayout()); 
        
        //Label
        JLabel label = new JLabel("Tamaño matriz (N x N):");
        
        //Input
        JTextField input = new JTextField();
        input.setText("");
        input.setPreferredSize(new Dimension(100, 20));
        input.addKeyListener(new KeyAdapter() {
        public void keyTyped(KeyEvent e) {
            char c = e.getKeyChar();
            if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                e.consume();  // if it's not a number, ignore the event
            }
         }
        });
        
        //Button
        JButton btn = new JButton("Redimensionar");
        btn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (!"".equals(input.getText())) {
                     int tam = Integer.parseInt(input.getText());
                
                    if (tam > 0) {
                         try { 
                             prog.getModel().setDimension(tam);
                             prog.getView().repaint();
                         } catch (IOException ex) {
                             Logger.getLogger(DimensionForm.class.getName()).log(Level.SEVERE, null, ex);
                         }
                       JOptionPane.showMessageDialog(null, "Tamaño actualizado");
                    } else {
                        JOptionPane.showMessageDialog(null, "Tamaño incorrecto!");
                    }      
                }                
            }
        });                
        this.setVisible(false);
        this.setResizable(false);
        this.setSize(350, 100);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        this.add(label);
        this.add(input);
        this.add(btn);
    }
}
