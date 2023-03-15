/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class Lectura {
    
    public ObjectInputStream ois;
    
    public Lectura(String file) throws FileNotFoundException, IOException{
        ois = new ObjectInputStream(new FileInputStream(file));
    }
    
    public Object readObject() throws IOException, ClassNotFoundException{
        return ois.readObject();
    }
    
    public void close() throws IOException{
        ois.close();
    }
    
}
