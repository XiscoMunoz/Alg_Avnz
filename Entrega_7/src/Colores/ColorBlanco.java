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
public class ColorBlanco extends ColorBase {
    
    static private String raw = "255-255-255"
            + "#" + "250-250-250"
            + "#" + "245-245-245"
            + "#" + "240-240-240"
            + "#" + "235-235-235"
            + "#" + "230-230-230";
    
    public ColorBlanco() {
        super("Blanco",raw); 
    }
}
