/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Principal.Eventos;
import Principal.Main;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class Vista extends JFrame implements ActionListener, Eventos, KeyListener, MouseListener {

    private Main prog;
    private Matriz matriz;
    private DimensionForm dim;

    public Vista(String s, Main p) {
        super(s);
        prog = p;
        this.dim = new DimensionForm(this.prog);
        this.getContentPane().setLayout(new BorderLayout()); 
        addKeyListener(this);
        //--
        //Panel de botones
        JPanel buttonPane = new JPanel();
        
        JButton setDimension = new JButton("Dimension");
        setDimension.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                dim.setVisible(true);
            }
   
        });
        setDimension.addKeyListener(this);
        JButton mezclar = new JButton("Mezclar");
        mezclar.addActionListener(this);
        mezclar.addKeyListener(this);
        JButton resolver = new JButton("Resolver");
        resolver.addActionListener(this);
        resolver.addKeyListener(this);
        
        buttonPane.add(setDimension);
        buttonPane.add(mezclar);
        buttonPane.add(resolver);
        buttonPane.addKeyListener(this);
        
        //Matriz
        matriz = new Matriz(prog.getModel());
        matriz.addKeyListener(this);
        matriz.addMouseListener(this);

        this.add(BorderLayout.NORTH, buttonPane);
        this.add(BorderLayout.CENTER, matriz);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void mostrar() {
        this.setSize(705, 764);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        try {
            Thread.sleep(500);
        } catch (Exception e) {
            Principal.Error.informaError(e);
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

    @Override
    public void notificar(String s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void keyPressed(KeyEvent ke) {
        int pos[][] = null;
        if (ke.getKeyChar() == 'a') {
            pos = this.prog.getModel().movIzquierda(this.prog.getModel().getInicial());
        }
        if (ke.getKeyChar() == 'd') {
            pos =this.prog.getModel().movDerecha(this.prog.getModel().getInicial());
        }
        if (ke.getKeyChar() == 'w') {
            pos = this.prog.getModel().movArriba(this.prog.getModel().getInicial());
        }
        if (ke.getKeyChar() == 's') {
            pos = this.prog.getModel().movAbajo(this.prog.getModel().getInicial());
        }

        if (pos != null) {
            this.prog.getModel().getInicial().setEstado(pos);
        }

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }
    
    @Override
    public void mouseClicked(MouseEvent me) {
        int indiceX, indiceY;
        int pos[][] = null;
        indiceX = me.getX() / this.prog.getModel().getMultX();
        indiceY = me.getY() / this.prog.getModel().getMultY();
        if (indiceX == this.prog.getModel().getInicial().getPosiX() + 1 && indiceY == this.prog.getModel().getInicial().getPosiY()) {
            pos = this.prog.getModel().movDerecha(this.prog.getModel().getInicial());
        } else if (indiceX == this.prog.getModel().getInicial().getPosiX() - 1 && indiceY == this.prog.getModel().getInicial().getPosiY()) {
            pos = this.prog.getModel().movIzquierda(this.prog.getModel().getInicial());
        } else if (indiceX == this.prog.getModel().getInicial().getPosiX() && indiceY == this.prog.getModel().getInicial().getPosiY() - 1) {
            pos = this.prog.getModel().movArriba(this.prog.getModel().getInicial());
        } else if (indiceX == this.prog.getModel().getInicial().getPosiX() && indiceY == this.prog.getModel().getInicial().getPosiY() + 1) {
            pos = this.prog.getModel().movAbajo(this.prog.getModel().getInicial());
        }
        if (pos != null) {
            this.prog.getModel().getInicial().setEstado(pos);
        }
          // pr.pintar(pr.inicial);
        this.prog.getView().repaint();
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }
}