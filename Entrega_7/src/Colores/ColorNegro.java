/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Colores;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class ColorNegro extends ColorBase {
    
    static private String raw = "0-0-0"
            + "#" + "5-5-5"
            + "#" + "10-10-10"
            + "#" + "15-15-15"
            + "#" + "20-20-20"
            + "#" + "25-25-25";
    
    public ColorNegro() {
        super("Negro",raw); 
    }
}
