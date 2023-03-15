/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npiezas;

import datos.Datos;
import npiezas.gui.Gui;
import npiezas.gui.Modal;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class NPiezas implements Notificar {

    private Gui gui;
    private Datos dat;

    private void inicio() {
        dat = new Datos();
        gui = construirInterfaz();
        gui.visualizar();
    }

    private Gui construirInterfaz() {
        Gui gui = new Gui("Recorrido de una pieza por un tablero de NxN", 600, 600, dat, this);
        gui.ponOpcion("imagenes/elijepieza.png", "barra:elije-pieza",
                "Elije la pieza del juego", "");
        gui.ponOpcion("imagenes/elijedim.png", "barra:elije-dim",
                "Elije la dimensión del tablero", "");
        gui.ponOpcion("imagenes/play.jpg", "barra:calcular",
                "Resuelve el problema", "");
        return gui;
    }

    public static void main(String[] args) {
        (new NPiezas()).inicio();
    }

    @Override
    public void notificar(String s) {
        if (s.contentEquals("barra:calcular")) {
            Juego ju = new Juego(dat, this);
            ju.start();
        } else if (s.contentEquals("repintar")) {
            gui.repintar();
        } else if (s.contentEquals("barra:elije-dim")) {
            Modal ventNivel = new Modal(this, "modal:dimension-", dat.getDimension());
            ventNivel.setVisible(true);
        } else if (s.contentEquals("barra:elije-pieza")) {
            Modal ventNivel = new Modal(this, "modal:pieza-", dat);
            ventNivel.setVisible(true);
        } else if (s.startsWith("modal:pieza-")) {
            String aux = s.substring(s.indexOf("-") + 1);
            dat.setClasePieza(aux);
            dat.regenerar(dat.getDimension());
            gui.setImagenPieza(dat);
            //gui.repintar();
        } else if (s.startsWith("modal:dimension-")) {
            String aux = s.substring(s.indexOf("-") + 1);
            dat = new Datos(Integer.parseInt(aux));
            gui.setDatos(dat);
            gui.repintar();
        } else if (s.startsWith("ponalerta:")) {
            String aux = s.substring(s.indexOf(":") + 1);
            Modal ventalert = new Modal(this, aux);
            ventalert.setVisible(true);
        }
    }
}
