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
public abstract class ColorBase {

    private String nombre;
    private ArrayList<Color> valores;
    private String raw = ""; // colores aproximados: "r-g-b#...#r-g-b"

    public ColorBase(String s, String r) {
        nombre = s;
        raw = r;
        valores = new ArrayList<>();
        cargarColores();
    }

    private void cargarColores() {
        valores = new ArrayList<>();
        String vals[] = raw.split("#");
        for (int i = 0; i < vals.length; i++) {
            String aux[] = vals[i].split("-");
            valores.add(new Color(Integer.parseInt(aux[0]),
                    Integer.parseInt(aux[1]), Integer.parseInt(aux[2])));
        }
    }

    public int size() {
        return valores.size();
    }

    public Color get(int i) {
        return valores.get(i);
    }
    
    public String getNombre() {
        return nombre;
    }

    public double distanciaAColor(Color c) {
        double res = 0.0;
        double dm = Double.MAX_VALUE;
        double r, g, b;
        r = c.getRed();
        g = c.getGreen();
        b = c.getBlue();
        for (int i = 0; i < valores.size(); i++) {
            res = Math.sqrt(((r - valores.get(i).getRed()) * (r - valores.get(i).getRed()))
                    + ((g - valores.get(i).getGreen()) * (g - valores.get(i).getGreen()))
                    + ((b - valores.get(i).getBlue()) * (b - valores.get(i).getBlue())));
            if (res < dm) {
                dm = res;
            }
        }
        res = dm;
        return res;
    }
}
