/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Principal.Eventos;
import Principal.Main;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class ProgressBar extends JFrame implements Eventos {

    private Main prog;
    private JProgressBar barra;
    
    public ProgressBar(Main prog) {
        super("Creación Base de Datos");
        this.prog = prog;
        this.initComponents();
        this.setVisible(true);
    }
    
    public void initComponents() {
        this.getContentPane().setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);
        this.setSize(350, 75);
        this.setDefaultCloseOperation(0);
       
        
        JLabel progress = new JLabel("Progreso: ");
        this.add(progress);
        
        barra = new JProgressBar(0, prog.getModel().getTotBar());
        barra.setValue(0);
        barra.setStringPainted(true);
        this.add(barra);
    }
    
    public void actualizarBarra(int idx){
           barra.setValue(idx);
    }

   
    @Override
    public void notificar(String s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
