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
public class ColorAzul extends ColorBase {
    
    static private String raw = "000-000-255"
            + "#" + "005-005-250"
            + "#" + "010-010-245"
            + "#" + "027-085-131"
            + "#" + "063-136-143"
            + "#" + "006-057-113"
            + "#" + "034-113-179"
            + "#" + "059-131-189"
            + "#" + "002-086-105"
            + "#" + "062-095-138"
            + "#" + "030-045-110"
            + "#" + "032-033-079"
            + "#" + "042-046-075";
    
    public ColorAzul() {
        super("Azul",raw); 
    }
}
