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
import Principal.Eventos;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class Vista extends JFrame implements ActionListener, Eventos {

    private Main prog;
    private JProgressBar barra;
    private JTextField num1;
    private JTextField num2;
    private JLabel error;
    private JTextArea solution;
    private RandomFrame rf;
    
    public Vista(String s, Main p) {
        super(s);
        prog = p;
        this.getContentPane().setLayout(new BorderLayout());
        JPanel inputs = new JPanel();
        //--
        
        //Input number 1
        num1 = new JTextField("",20);
        num1.addKeyListener(new KeyAdapter() {
        public void keyTyped(KeyEvent e) {
            char c = e.getKeyChar();
            if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                e.consume();  // if it's not a number, ignore the event
            }
         }
        });
        inputs.add(num1);
        
        //X label
        JLabel x = new JLabel("   X   ");
        inputs.add(x);
        
        //Input number 2
        num2 = new JTextField("",20);
        num2.addKeyListener(new KeyAdapter() {
        public void keyTyped(KeyEvent e) {
            char c = e.getKeyChar();
            if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                e.consume();  // if it's not a number, ignore the event
            }
         }
        });
        inputs.add(num2);
        
        //Button random
        JButton btnRandom = new JButton("Random");
        btnRandom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rf = new RandomFrame(prog);
            }
        });
        btnRandom.setVisible(true);
        inputs.add(btnRandom);
        
        //Button clean
        JButton btnClean = new JButton("Limpiar");
        btnClean.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                num1.setText("");
                num2.setText("");
                solution.setText("");
                error.setVisible(false);
            }
        });
        btnClean.setVisible(true);
        inputs.add(btnClean);
        
        JPanel centralPanel = new JPanel();
        centralPanel.setSize(500, 500);
        centralPanel.setLayout(new BorderLayout());
        
        JPanel iuPanel = new JPanel();
        iuPanel.setLayout(new BorderLayout());
        
        error = new JLabel("Por favor, rellena los 2 campos antes de iniciar la ejecución.");
        error.setForeground(Color.red);
        error.setVisible(false);
        iuPanel.add(BorderLayout.NORTH, error); 
        
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayout());
        
        JButton btnNormal = new JButton("Normal");
        btnNormal.addActionListener(this);
        btnNormal.setVisible(true);
        btnPanel.add(btnNormal);
        
        JButton btnKaratsuba = new JButton("Karatsuba");
        btnKaratsuba.addActionListener(this);
        btnKaratsuba.setVisible(true);
        btnPanel.add(btnKaratsuba);
        
        JButton btnMixto = new JButton("Mixto");
        btnMixto.addActionListener(this);
        btnMixto.setVisible(true);
        btnPanel.add(btnMixto);
        
        iuPanel.add(BorderLayout.CENTER, btnPanel);
        centralPanel.add(BorderLayout.NORTH, iuPanel);
        
        solution = new JTextArea();
        solution.setEditable(false);
        solution.setVisible(true);
        
        JScrollPane sp = new JScrollPane(solution);
        centralPanel.add(BorderLayout.CENTER, sp);
        
        barra = new JProgressBar();
        barra.setString("");
        barra.setStringPainted(true);
        //--
        this.add(BorderLayout.NORTH, inputs);
        
        this.add(BorderLayout.CENTER, centralPanel);
        
        this.add(BorderLayout.SOUTH, barra);
       
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void añadirTexto(String s) {
        this.solution.append(s + "\n");
    }
    
    public String getNum1() {
        return this.num1.getText();
    }
    
    public String getNum2() {
        return this.num2.getText();
    }
    
    public void setRandomNum1(int cifras) {
        Random ran = new Random();
        String random = "";
        
        for (int i = 0; i < cifras; i++) {
            random = random + String.valueOf(ran.nextInt(9));
        }
        this.num1.setText(random);
    }
    
    public void setRandomNum2(int cifras) {
        Random ran = new Random();
        String random = "";
        
        for (int i = 0; i < cifras; i++) {
            random = random + String.valueOf(ran.nextInt(9));
        }
        this.num2.setText(random);
    }

    public void mostrar() {
        //this.pack();
        this.setSize(700, 500);
        this.setResizable(false);
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
        
        if ("".equals(num1.getText()) || "".equals(num2.getText())) {
            error.setVisible(true);
        } else {
            error.setVisible(false);
            
            String comanda = e.toString();
            int a = comanda.indexOf(",cmd=") + 5;
            comanda = comanda.substring(a, comanda.indexOf(",", a));
            prog.notificar(comanda);
        }
        
    }

    @Override
    public void notificar(String s) {
        if (s.startsWith("Start")) {
            this.barra.setIndeterminate(true);
        } else if (s.startsWith("End")) {
            this.barra.setIndeterminate(false);
        }
    }
}
