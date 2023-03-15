/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import Principal.Main;
import Principal.Error;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class Vista extends JFrame implements ActionListener {

    private Main prog;
    private JProgressBar barra;

    public Vista(String s, Main p) {
        super(s);
        prog = p;
        this.getContentPane().setLayout(new BorderLayout());
        JPanel bots = new JPanel();
        //--
        JButton boto_o_n = new JButton("O(n)");
        boto_o_n.addActionListener(this);
        bots.add(boto_o_n);
        
        JButton boto_o_log_n = new JButton("O(logn)");
        boto_o_log_n.addActionListener(this);
        bots.add(boto_o_log_n);
        
        JButton boto_o_n_2 = new JButton("O(n2)");
        boto_o_n_2.addActionListener(this);
        bots.add(boto_o_n_2);
        
        JButton boto_o_n_log_n = new JButton("O(nlogn)");
        boto_o_n_log_n.addActionListener(this);
        bots.add(boto_o_n_log_n);
        
        barra = new JProgressBar(0, 100);
        barra.setValue(0);
        barra.setStringPainted(true);
        //--
        this.add(BorderLayout.NORTH, bots);
        PanelDibujo panell = new PanelDibujo(800, 400, prog.getModel(), this);
        
        this.add(BorderLayout.CENTER, panell);
        
        this.add(BorderLayout.SOUTH, barra);
       
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void actualizarBarra(int valor) {
        barra.setValue(valor);
    }

    public void mostrar() {
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            Error.informaError(e);
        }
        this.revalidate();
        this.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comanda = e.toString();
        int a = comanda.indexOf(",cmd=") + 5;
        comanda = comanda.substring(a, comanda.indexOf(",", a));
        prog.notificar(comanda);
    }

  
}
