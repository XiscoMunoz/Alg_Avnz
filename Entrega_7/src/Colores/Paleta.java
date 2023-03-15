/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Colores;

import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class Paleta {
    private ArrayList <ColorBase> cols;
    
    public Paleta() {
        cols = new ArrayList <> ();
        cols.add(new ColorBlanco());
        cols.add(new ColorNegro());
        cols.add(new ColorRojo());
        cols.add(new ColorVerde());
        cols.add(new ColorAzul());
        cols.add(new ColorAmarillo());
        cols.add(new ColorNaranja());
    }
    
    public int getNColores() {
        return cols.size();
    }
    
    public String getNombre(int i) {
        return cols.get(i).getNombre();
    }
    
    public int analizarColor(Color col) {
        int res = -1;
        double dm = Double.MAX_VALUE;
        int poscolor = -1;
        double aux;
        for(int j=0;j<cols.size();j++) {
            for(int i=0;i<cols.get(j).size();i++) {
                aux = cols.get(j).distanciaAColor(col);
                if(aux < dm) {
                    dm = aux;
                    poscolor = j;
                }
            }
        }
        res = poscolor;
        return res;
    }
}
