/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Modelo.Nodo;
import Principal.Eventos;
import Principal.Main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.Utilities;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class Vista extends JFrame implements ActionListener, Eventos {

    private Main prog;
    private JTextPane areaTexto;
    private JTextArea output;
    private StyledDocument doc;
    private Style errores;
    private Style cambios;
    private SimpleAttributeSet basicStyle;

    public Vista(String s, Main p) {
        super(s);
        prog = p;
        this.getContentPane().setLayout(new BorderLayout());
        //--

        //Panel de botones
        JPanel btnPanel = new JPanel();
        JButton detectar = new JButton("Detectar");
        detectar.addActionListener(this);

        JButton corregir = new JButton("Corregir");
        corregir.addActionListener(this);

        JButton limpiar = new JButton("Limpiar");

        btnPanel.add(detectar);
        btnPanel.add(corregir);

        //Panel de texto     
        JPanel centralPanel = new JPanel();
        centralPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        centralPanel.setLayout(new BorderLayout());

        areaTexto = new JTextPane();
        areaTexto.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        //Estilos
        basicStyle = new SimpleAttributeSet();
        StyleConstants.setForeground(basicStyle, Color.black);
        StyleConstants.setBold(basicStyle, true);
        StyleConstants.setFontSize(basicStyle, 18);

        areaTexto.setCharacterAttributes(basicStyle, true);
        areaTexto.addMouseListener(crearMenu(areaTexto));

        doc = areaTexto.getStyledDocument();
        errores = areaTexto.addStyle("erroneas", null);
        StyleConstants.setBold(errores, true);
        StyleConstants.setFontSize(errores, 18);
        StyleConstants.setForeground(errores, Color.red);
        StyleConstants.setUnderline(errores, true);

        cambios = areaTexto.addStyle("cambios", null);
        StyleConstants.setBold(cambios, true);
        StyleConstants.setFontSize(cambios, 18);
        StyleConstants.setForeground(cambios, Color.blue);
        StyleConstants.setUnderline(cambios, true);

        JScrollPane sp = new JScrollPane(areaTexto);
        centralPanel.add(sp);

        //Panel informativo
        JPanel southPane = new JPanel();
        southPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        southPane.setLayout(new BorderLayout());

        output = new JTextArea();
        output.setEditable(false);
        output.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        southPane.add(output);

        limpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                areaTexto.setText("");
                setOutput("");
                areaTexto.setCharacterAttributes(basicStyle, true);
            }
        });
        btnPanel.add(limpiar);

        this.add(BorderLayout.NORTH, btnPanel);
        this.add(BorderLayout.CENTER, centralPanel);
        this.add(BorderLayout.SOUTH, southPane);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void mostrar() {
        this.setSize(700, 500);
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

    public String getText() {
        return this.areaTexto.getText();
    }

    public void setOutput(String text) {
        this.output.setText(text);
    }

    public void replace(String pal, String correct, int index) {
        this.areaTexto.select(index - pal.length(), index);
        this.areaTexto.replaceSelection("");
        try {
            doc.insertString(index - pal.length(), correct, cambios);
        } catch (BadLocationException ex) {
            Logger.getLogger(Vista.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void markIncorrect(ArrayList<Nodo> incorrectas) {
        Nodo aux;

        for (int i = 0; i < incorrectas.size(); i++) {
            aux = incorrectas.get(i);
            this.areaTexto.select(aux.getIndex() - aux.getPalabra().length(), aux.getIndex());
            this.areaTexto.replaceSelection("");
            try {
                doc.insertString(aux.getIndex() - aux.getPalabra().length(), aux.getPalabra(), errores);
            } catch (BadLocationException ex) {
                Logger.getLogger(Vista.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private MouseListener crearMenu(JTextPane tp) {
        return new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                //Declaraciones
                JPopupMenu popupMenu;
                JMenuItem[] JMI;
                ArrayList<String> propuestas;

                //Instrucciones
                tp.setCaretPosition(tp.viewToModel(e.getPoint()));

                if (e.isPopupTrigger()) {
                    try { //Click derecho
                        String pal = tp.getText(Utilities.getWordStart(tp, tp.getCaretPosition()), Utilities.getWordEnd(tp, tp.getCaretPosition()) - Utilities.getWordStart(tp, tp.getCaretPosition()));
                        if (prog.getModel().isErronea(pal)) {
                            popupMenu = new JPopupMenu();
                            propuestas = prog.getModel().getPalabras_propuestas(pal);
                            JMI = new JMenuItem[propuestas.size()];
                            for (int i = 0; i < propuestas.size(); i++) {
                                JMI[i] = new JMenuItem(propuestas.get(i));
                                popupMenu.add(JMI[i]);
                            }
                            for (int i = 0; i < JMI.length; i++) {
                                String texto = JMI[i].getText();
                                JMI[i].addActionListener(e3 -> {
                                    try {
                                        prog.getModel().removePalabraErronea(pal, Utilities.getWordEnd(tp, tp.getCaretPosition()));
                                        replace(pal, texto, Utilities.getWordEnd(tp, tp.getCaretPosition()));
                                    } catch (BadLocationException ex) {
                                        Logger.getLogger(Vista.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                });
                            }
                            popupMenu.show(tp, e.getX(), e.getY());
                        }
                    } catch (BadLocationException ex) {
                        Logger.getLogger(Vista.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
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
}
