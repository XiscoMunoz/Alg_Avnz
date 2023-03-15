/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos.piezas;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class Caballo extends Pieza {

    public Caballo() {
        nombre = this.getClass().getName();
        imagen = "/imagenes/caballo.png";
        movx = new int[8];
        movy = new int[8];
        int pos = 0;
        movx[pos] = 1; 
        movy[pos++] = -2;            
        movx[pos] = 2; 
        movy[pos++] = -1;            
        movx[pos] = 2; 
        movy[pos++] = 1;            
        movx[pos] = 1; 
        movy[pos++] = 2;        
        movx[pos] = -1; 
        movy[pos++] = 2;            
        movx[pos] = -2; 
        movy[pos++] = 1;            
        movx[pos] = -2; 
        movy[pos++] = -1;            
        movx[pos] = -1; 
        movy[pos++] = -2;            
    }
}
