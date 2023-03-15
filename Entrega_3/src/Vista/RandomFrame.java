package Vista;

import Principal.Eventos;
import Principal.Main;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */

public final class RandomFrame extends JFrame implements Eventos{
    
    private Main prog;
    private JLabel error;
    private JTextField nValue;
    private JTextField mValue;

    
    public RandomFrame(Main prog) {
        super("Random Numbers");
        this.prog = prog;
        this.initComponents();
        this.setVisible(true);
    }
    
    public void initComponents() {
        this.getContentPane().setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);
        this.setSize(350, 100);
        
        JLabel n = new JLabel("Cifras de N");
        this.add(n);
        
        nValue = new JTextField("", 5);
        nValue.addKeyListener(new KeyAdapter() {
        public void keyTyped(KeyEvent e) {
            char c = e.getKeyChar();
            if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                e.consume();  // if it's not a number, ignore the event
            }
         }
        });
        this.add(nValue);
        
        JLabel m = new JLabel("Cifras de M");
        this.add(m);
        
        mValue = new JTextField("", 5);
        mValue.addKeyListener(new KeyAdapter() {
        @Override
        public void keyTyped(KeyEvent e) {
            char c = e.getKeyChar();
            if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                e.consume();  // if it's not a number, ignore the event
            }
         }
        });
        this.add(mValue);
        
        JButton btnSetRandom = new JButton("Set Random");
        btnSetRandom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ("".equals(nValue.getText()) || "".equals(mValue.getText())) {
                    error.setVisible(true);
                } else {
                    prog.notificar("SetRandom:"+nValue.getText()+","+mValue.getText());
                    dispose();
                }            
            }
        });
        this.add(btnSetRandom);
        
        error = new JLabel("Campos vacíos");
        error.setForeground(Color.red);
        error.setVisible(false);
        this.add(error);
    }

    @Override
    public void notificar(String s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
