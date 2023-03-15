/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Principal.Eventos;
import Principal.Main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class Vista extends JFrame implements ActionListener, Eventos {

    private Main prog;

    private JPanel panelImgSelect;
    private JPanel panelImgResul;
    private String urlImagen;
    private boolean condicion = false;
    private int indice = 0;
    private ProgressBar pb;

    public Vista(String s, Main p) {
        super(s);
        prog = p;

        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setBackground(Color.LIGHT_GRAY);
        JPanel panelOpciones = new JPanel();
        panelOpciones.setLayout(new BoxLayout(panelOpciones, BoxLayout.X_AXIS));

        JLabel fileLabel = new JLabel("Arrastra un archivo al cuadro izquierdo");
        fileLabel.setMaximumSize(new Dimension(300, 25));
        JButton botonEjecutar = new JButton("Ejecutar");
        botonEjecutar.addActionListener(this);

        panelOpciones.setBorder(new EmptyBorder(10, 0, 0, 0));

        panelOpciones.add(fileLabel);
        panelOpciones.add(Box.createRigidArea(new Dimension(50, 0)));
        panelOpciones.add(botonEjecutar);

        JPanel panelImagenes = new JPanel();
        panelImagenes.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelImagenes.setLayout(new BoxLayout(panelImagenes, BoxLayout.X_AXIS));
        panelImagenes.setMaximumSize(new Dimension(700, 500));

        panelImgSelect = new JPanel();
        panelImgSelect.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
        panelImgSelect.setBackground(Color.white);

        panelImgSelect.setDropTarget(new DropTarget() {
            @Override
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    List<File> droppedFiles = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    for (File file : droppedFiles) {

                        urlImagen = file.getPath();
                        System.out.println(urlImagen);
                        
                        //Se crea la imagen desde el fichero
                        File img = new File(file.getPath());
                        BufferedImage image = ImageIO.read(img);
                        prog.getModel().setImagenElegida(image);

                        Image redInput = new AffineTransformOp(
                                AffineTransform.getScaleInstance((double) panelImgSelect.getWidth() / image.getWidth(), (double) panelImgSelect.getHeight() / image.getHeight()),
                                AffineTransformOp.TYPE_BICUBIC).filter(image, null);
                        
                        //Se transforma en un ImagenIcon y la redimensionamos
                        ImageIcon picLabel = new ImageIcon(redInput, img.toString());

                        picLabel.setImage(picLabel.getImage().getScaledInstance(panelImgSelect.getWidth(), panelImgSelect.getHeight(), Image.SCALE_SMOOTH));
                        
                        //Creamos la etiqueta para ponerla en el jLabel
                        JLabel etiImg = new JLabel();
                        etiImg.setOpaque(true);
                        etiImg.setSize(panelImgSelect.getWidth(), panelImgSelect.getHeight());
                        etiImg.setIcon(picLabel);

                        panelImgSelect.add(etiImg);
                        panelImgSelect.repaint();
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        panelImgResul = new JPanel();
        panelImgResul.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
        panelImgResul.setBackground(Color.white);

        panelImgResul.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {

                if (condicion) {

                    try {
                        pintar();
                    } catch (Exception ex) {
                        System.out.println("ERROR EN LA FUNCION CLICK");
                    }
                } else {
                    System.out.println("Aun no puedes");
                }
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
        });

        panelImagenes.add(panelImgSelect);
        panelImagenes.add(Box.createRigidArea(new Dimension(25, 0)));
        panelImagenes.add(panelImgResul);

        this.add(panelOpciones);
        this.add(panelImagenes);
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
    public void progressBar(){
        pb = new ProgressBar(prog);
    }

    @Override
    public void notificar(String s) {
        if (s.startsWith("Pinta")) {
            condicion = true;
            indice = 0;
            pintar();

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("".equals(urlImagen)) {
            System.out.println("NO IMAGEN SELECCIONADA");
        } else {
            String comanda = e.toString();
            int a = comanda.indexOf(",cmd=") + 5;
            comanda = comanda.substring(a, comanda.indexOf(",", a));
            prog.notificar(comanda);

        }
    }

    public void pintar() {

        try {
            
            File img = new File("flags/" + prog.getModel().getNombre(indice));
            BufferedImage image = ImageIO.read(img);

            Image redInput = new AffineTransformOp(
                    AffineTransform.getScaleInstance((double) panelImgResul.getWidth() / image.getWidth(), (double) panelImgResul.getHeight() / image.getHeight()),
                    AffineTransformOp.TYPE_BICUBIC).filter(image, null);
            
            //La transformamos en un ImagenIcon y la redimensionameos
            ImageIcon picLabel = new ImageIcon(redInput, img.toString());
            picLabel.setImage(picLabel.getImage().getScaledInstance(panelImgResul.getWidth(), panelImgResul.getHeight(), Image.SCALE_SMOOTH));
            
            //Creamos la etiqueta para ponerla en el jLabel
            JLabel etiImg = new JLabel();
            etiImg.setOpaque(true);
            etiImg.setSize(panelImgResul.getWidth(), panelImgResul.getHeight());
            etiImg.setIcon(picLabel);

            panelImgResul.add(etiImg);
            panelImgResul.repaint();
            indice++;
            if (indice == prog.getModel().getLonguitud()) {
                indice = 0;
            }
        } catch (Exception ex) {
            System.out.println("ERROR EN LA FUNCION PINTAR");
        }
    }

    public ProgressBar getPb() {
        return pb;
    }

    
}
