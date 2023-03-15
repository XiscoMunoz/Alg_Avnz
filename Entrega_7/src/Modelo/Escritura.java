/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class Escritura {
    
    public ObjectOutputStream oos;
    
    public Escritura(String file) throws FileNotFoundException, IOException{
        oos = new ObjectOutputStream(new FileOutputStream(file));
    }
    
    public void writeObject(Object o) throws IOException{
        oos.writeObject(o);
    }
    
    public void closeFile() throws IOException{
        oos.close();
    }
    
}
