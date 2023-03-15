/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Principal.Eventos;
import Principal.Main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class Vista extends JFrame implements ActionListener, Eventos {
    
    private Main prog;
    private JTextArea solution;
    private JTextField fileChooser;
    private JLabel error;

    
    public Vista(String s, Main p) {
        super(s);
        prog = p;
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        //--
        
        //Selector de archivo
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setBorder(new EmptyBorder(new Insets(50, 0, 50, 0)));
        
        JLabel fileLabel = new JLabel("Arrastra un archivo:");
        fileLabel.setMaximumSize(new Dimension(130, 50));
        
        fileChooser = new JTextField();
        fileChooser.addKeyListener(new KeyAdapter() {
        public void keyTyped(KeyEvent e) {
            e.consume();
         }
        });
        fileChooser.setMaximumSize(new Dimension(300, 50));
        fileChooser.setDropTarget(new DropTarget() {
            @Override
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    List<File> droppedFiles = (List<File>)
                        evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    for (File file : droppedFiles) {
                        fileChooser.setText(file.getPath());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        JButton btnCompress = new JButton("Comprimir");
        btnCompress.addActionListener(this);
        
        JButton btnUnzip = new JButton("Descomprimir");
        btnUnzip.addActionListener(this);
        
        //Button clean
        JButton btnClean = new JButton("Limpiar");
        btnClean.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.setText("");
                solution.setText("");
                error.setVisible(false);
            }
        });
        
        topPanel.add(fileLabel);
        topPanel.add(fileChooser);
        topPanel.add(btnCompress);
        topPanel.add(btnUnzip);
        topPanel.add(btnClean);
        
        //Mensaje de error
        error = new JLabel("Por favor, arrastre un archivo para comprimir / descomprimir.");
        error.setForeground(Color.red);
        error.setVisible(false);
        
        //Panel de solución
        this.solution = new JTextArea();
        this.solution.setEditable(false);
        this.solution.setVisible(true);
        
        JScrollPane sp = new JScrollPane(this.solution);
        
        JPanel botPanel = new JPanel();
        botPanel.setLayout(new BorderLayout());
        
        botPanel.add(BorderLayout.NORTH, error);
        botPanel.add(BorderLayout.CENTER, sp);
        
        
        this.add(topPanel);      
        this.add(botPanel);
           
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public String getPathFile() {
        return this.fileChooser.getText();
    }
    
    public void añadirTexto(String s) {
        this.solution.append(s + "\n");
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
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if ("".equals(this.fileChooser.getText())) {
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
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
