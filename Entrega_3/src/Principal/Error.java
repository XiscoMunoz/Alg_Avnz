/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;

/**
 *
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class Error {

    static public void informaError(Exception ex) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        String slogs = writer.toString();
        System.err.println("Se ha producido un error. El error es:\n" + slogs);
        try {
            FileWriter fr = new FileWriter("logs.txt", true);
            BufferedWriter br = new BufferedWriter(fr);
            br.write("********************** Error date: " + (new Date()).toString());
            br.newLine();
            br.write(slogs);
            br.write("********************** End error report");
            br.newLine();
            br.newLine();
            br.close();
            fr.close();
        } catch (Exception e) {
            System.err.println("Error en la gestión de logs.");
            e.printStackTrace();
        }
    }
}
