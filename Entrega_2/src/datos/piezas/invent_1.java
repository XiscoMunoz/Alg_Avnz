/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos.piezas;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class invent_1 extends Pieza {

    public invent_1() {
        nombre = this.getClass().getName();
        imagen = "/imagenes/invent_1.png";
        movx = new int[6];
        movy = new int[6];
        int pos = 0;
        movx[pos] = 0; 
        movy[pos++] = -1;            
        movx[pos] = 1; 
        movy[pos++] = -1;
        movx[pos] = 1; 
        movy[pos++] = 1;
        movx[pos] = 0; 
        movy[pos++] = 1;
        movx[pos] = -1; 
        movy[pos++] = 1;   
        movx[pos] = -1; 
        movy[pos++] = -1;  
        
        
        
    }
}
