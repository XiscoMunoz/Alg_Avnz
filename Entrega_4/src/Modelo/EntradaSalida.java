/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class EntradaSalida {

    public Hashtable hash;
    String[] nombreFichero;
    public File fichero;
    public File ficheroDescompreso;

    public RandomAccessFile RAS;
    public RandomAccessFile RASE;

    public Byte byteLeido;
    public Byte byteEscrito;
    public long maximo;
    public int cantidad;
    public boolean auxliarEscritura = false;
    public long tamComprimido = 0;

    public EntradaSalida(Hashtable hash, String nombre) {

        this.hash = hash;
        fichero = new File(nombre);
        nombreFichero = nombre.split("\\.", 2);
        maximo = fichero.length();
    }
    
    public EntradaSalida() {
        
    }

    public Hashtable getHash() {
        return hash;
    }

    public void setHash(Hashtable hash) {
        this.hash = hash;
    }

    public long getMaximo() {
        return maximo;
    }

    public void leer() {
        int leidos = 0;
        int valor = 0;
        try {

            RAS = new RandomAccessFile(fichero, "rw");
            byte[] fileContent;
            cantidad = 512; // esto me pilla todo el tamaño del archivo, lo carga todo en memoria igual

            int n;
            while (leidos < maximo) {
                fileContent = new byte[cantidad];
                n = RAS.read(fileContent, 0, cantidad);

                if (n == -1) { //Si es -1 significa que ha podido leer todo, si no devuelve el nuemero de bytes leidos
                    n = cantidad;
                }

                for (int i = 0; i < n; i++) {
                    byteLeido = fileContent[i];

                    if (hash.containsKey(byteLeido)) {
                        valor = (int) hash.get(byteLeido) + 1;
                        hash.put(byteLeido, valor);
                    } else {
                        hash.put(byteLeido, 1);
                    }
                }
                leidos = leidos + n;
            }

            RAS.close();
        } catch (Exception ex) {
            System.out.println("ERROR EN LA LECTURA DEL FICHERO");
        }
    }

    public void Comprimir(File fich,int desplazamiento) {
        int leidos = 0;
        String codificacion = "";
        String auxiliar = "";
        try {

            RAS = new RandomAccessFile(fichero, "rw");
            
            RASE = new RandomAccessFile(fich, "rw");
            RASE.seek(RASE.length());
            byte[] fileContent = Files.readAllBytes(fich.toPath());
            while (leidos < maximo) {

                if (auxiliar.length() < 8) { //CASO NORMAL DE CODIFICACION

                    byteLeido = RAS.readByte();

                    if (auxiliar.length() == 0) {//SE HA ESCRITO ANTES EXACTAMENTE UN BYTE
                        codificacion += (String) hash.get(byteLeido);

                    } else {//CUANDO SE HA ESCRITO EL BYTE SOBRABAN ALGUNOS Y TIENEN QUE IR AQUI DELANTE

                        codificacion += auxiliar;
                        codificacion += (String) hash.get(byteLeido);
                        auxiliar = "";
                    }

                    if (codificacion.length() == 8) {
                        RASE.writeByte((byte) Integer.parseInt(codificacion, 2));
                        auxiliar = "";
                        codificacion = "";

                    } else if (codificacion.length() > 8) {

                        auxiliar = codificacion.substring(8, codificacion.length());
                        RASE.writeByte((byte) Integer.parseInt(codificacion.substring(0, 8), 2));
                        codificacion = "";
                    }
                    leidos++;

                } else {//CASO EN QUE LA CODIFICACCION ES MUY GRANDE Y DEPUES DE`PARTIR EL STRING SIGUE HABIENDO SUFICIENTES BITS PARA ESCRIBIR UN BIT
                    RASE.writeByte((byte) Integer.parseInt(auxiliar.substring(0, 8), 2));
                    auxiliar = auxiliar.substring(8, auxiliar.length());

                }
            }
            codificacion = "";
            
            while (auxiliar.length() != 0) {
                if (auxiliar.length() < 8) { //CASO NORMAL DE CODIFICACION

                    RASE.seek(desplazamiento);
                    RASE.writeByte((byte) (8 - auxiliar.length()));
                    RASE.seek(RASE.length());
                    auxliarEscritura = true;

                    for (int i = auxiliar.length(); i < 8; i++) {

                        auxiliar = auxiliar + "0";
                    }
                    RASE.writeByte((byte) Integer.parseInt(auxiliar, 2));
                    auxiliar = "";

                } else {//CASO EN QUE LA CODIFICACCION ES MUY GRANDE Y DEPUES DE`PARTIR EL STRING SIGUE HABIENDO SUFICIENTES BITS PARA ESCRIBIR UN BIT
                    RASE.writeByte((byte) Integer.parseInt(auxiliar.substring(0, 8), 2));
                    auxiliar = auxiliar.substring(8, auxiliar.length());

                }
            }
            if (!auxliarEscritura) {
                RASE.seek(desplazamiento);
                RASE.writeByte(0);
                RASE.seek(RASE.length());
            }
            tamComprimido = RASE.length();
            RASE.close();
        } catch (Exception ex) {
            System.out.println("ERROR EN LA COMPRESION DEL ARCHIVO");
        }

    }

    public long tamComprimido() {

        return tamComprimido;
    }

    public void descomprimir(String nombre) {
        int leidos = 0;
        int restantes = 0;
        int longArbol = 0;
        int aux = 0;
        int longuitud;
        Nodo nodo;
        Nodo nodoAux;
        ArrayList<Nodo> lista = new ArrayList<>();

        String[] parts = nombre.replace("\\", "/").split("[/.]");
        String nombreAux = parts[parts.length-2];
        String auxiliar2 = "";
        try {

            RAS = new RandomAccessFile(nombre, "r");

            longuitud = RAS.readByte();
            leidos++;
            for (int i = 0; i < longuitud; i++) {
                auxiliar2 += (char) RAS.readByte();
                leidos++;
            }

            ficheroDescompreso = new File("FicheroDescompreso" + nombreAux + "." + auxiliar2);
            if (ficheroDescompreso.exists()) {
                ficheroDescompreso.delete();
            }

            RASE = new RandomAccessFile(ficheroDescompreso, "rw");
            byte[] fileContent;
            cantidad = 1; // esto me pilla todo el tamaño del archivo, lo carga todo en memoria igual
            maximo = RAS.length();
            int n;

            restantes = (RAS.readByte());
            leidos++;
            longArbol = (RAS.readInt());
            leidos = leidos + 4;

            for (int i = 0; i < longArbol; i++) {
                nodo = new Nodo(RAS.readByte(), RAS.readByte(), RAS.readByte(), RAS.readByte());
                lista.add(nodo);
            }
            leidos = leidos + (longArbol * 4);
            System.out.println("");
            while (lista.size() > 1) {
                if (lista.get(aux).IndiceIzq == -1 && lista.get(aux).IndiceDer == -1) {
                    lista.get(aux).setHijoDer(null);
                    lista.get(aux).setHijoIzq(null);
                } else {
                    lista.get(aux).setHijoIzq(lista.get(aux - 2));
                    lista.get(aux).setHijoDer(lista.get(aux - 1));
                    lista.remove(aux - 2);
                    aux = aux - 1;
                    lista.remove(aux - 1);
                    aux = aux - 1;
                }
                aux++;
            }
            nodo = lista.get(0);
            System.out.println("");
            nodoAux = nodo;
            while (leidos < maximo) {
                fileContent = new byte[cantidad];
                n = RAS.read(fileContent, 0, cantidad);

                if (n == -1) { //Si es -1 significa que ha podido leer todo, si no devuelve el nuemero de bytes leidos
                    n = cantidad;
                }

                if (nodoAux.getHijoIzq() == null && nodoAux.getHijoDer() == null) {
                    nodoAux = nodo;
                }

                byteLeido = fileContent[0];
                String s1 = String.format("%8s", Integer.toBinaryString(byteLeido & 0xFF)).replace(' ', '0');

                if (leidos != maximo - 1) {
                    for (int i = 0; i < s1.length(); i++) {

                        if (s1.charAt(i) == '0') {
                            nodoAux = nodoAux.getHijoIzq();
                        } else {
                            nodoAux = nodoAux.getHijoDer();
                        }
                        if (nodoAux.getHijoIzq() == null && nodoAux.getHijoDer() == null) {
                            RASE.writeByte(nodoAux.getBite());
                            nodoAux = nodo;
                        }
                    }
                } else {
                    for (int i = 0; i < s1.length() - restantes; i++) {

                        if (s1.charAt(i) == '0') {
                            nodoAux = nodoAux.getHijoIzq();
                        } else {
                            nodoAux = nodoAux.getHijoDer();
                        }
                        if (nodoAux.getHijoIzq() == null && nodoAux.getHijoDer() == null) {
                            RASE.writeByte(nodoAux.getBite());
                            nodoAux = nodo;
                        }
                    }
                }
                //  }
                leidos = leidos + n;
                //System.out.println(leidos);
            }
            RASE.close();
            RAS.close();
        } catch (Exception ex) {
            System.out.println("ERROR EN LA DESCOMPRESION DEL FICHERO");
        }

    }

}
