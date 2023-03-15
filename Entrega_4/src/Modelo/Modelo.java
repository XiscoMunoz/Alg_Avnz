/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Principal.Eventos;
import Principal.Main;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.util.Hashtable;
import java.util.PriorityQueue;
import java.util.Set;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class Modelo implements Eventos {
    
    private Main prog;
    
    private String codigo = "";
    private Hashtable<Byte, String> hashCodigo = new Hashtable();
    private int indice = 0;
    private RandomAccessFile RASE;
    private EntradaSalida ES;
    
    public Modelo(Main p) {
        prog = p;
    }
    
    public void comprimir(String file) throws FileNotFoundException, IOException, Exception {
        Hashtable hashFrecuencia = new Hashtable();
        PriorityQueue<Nodo> cola = new PriorityQueue<Nodo>();
        String nombreFichero = this.prog.getView().getPathFile().replace("\\", "/");
        String[] parts = nombreFichero.split("[/.]");
        String auxNombre[] = new String[] {parts[parts.length-2], parts[parts.length-1]};
        String extension = auxNombre[1];
        EntradaSalida ES = new EntradaSalida(hashFrecuencia, nombreFichero);

        int numeroAuxiliar = 0;
        Nodo nodo;
        Nodo arbol;
        Nodo auxiliar;
        double entropia = 0;
        double totalFrec;
        double probabilidad;
        int aux = 0;

        this.prog.getView().añadirTexto("Obteniendo frecuencia");
        ES.leer();
        hashFrecuencia = ES.getHash();
        this.prog.getView().añadirTexto("Tamaño antes de comprimir: "+ES.getMaximo());

        this.prog.getView().añadirTexto("Creando arbol - Calculando entropia");
        totalFrec = (int) ES.getMaximo();
        Set<Byte> setOfKeys = hashFrecuencia.keySet();

        for (Byte key : setOfKeys) {
            aux = (int) hashFrecuencia.get(key);
            nodo = new Nodo(key, aux);
            cola.add(nodo);
            probabilidad = (aux / totalFrec);
            entropia += probabilidad * (Math.log(1 / probabilidad) / Math.log(2));
        }
        this.prog.getView().añadirTexto("ENTROPIA = " + entropia);

        //TENER EN CUENTA QUE PUEDAN DAR RESULTADOS DIFERENETES A LOS QUE HACEMOS NOSOTROS, YA QUE SI HAY VARIOS ELEMENTOS CON LA MISMA FRECUENCIA CAMBIE LA ESTRUCTURA DEL ARBOL
        //ESTO AFECTA A AL CODIGO 
        while (cola.size() > 1) {
            auxiliar = new Nodo();
            numeroAuxiliar = cola.peek().getFreq();
            auxiliar.setHijoIzq(cola.poll());
            numeroAuxiliar += cola.peek().getFreq();
            auxiliar.setHijoDer(cola.poll());
            auxiliar.setFreq(numeroAuxiliar);
            cola.add(auxiliar);
        }
        arbol = cola.poll();
        
        File ficheroCompreso = new File("FicheroCompreso" + auxNombre[0] + ".dat");
            if (ficheroCompreso.exists()) {
                ficheroCompreso.delete();
            }

        this.prog.getView().añadirTexto("Creando codificacion");
        preorden(arbol);
        RASE = new RandomAccessFile(ficheroCompreso, "rw");
        RASE.writeByte(extension.length());
        for (int i = 0; i < extension.length(); i++) {
            RASE.writeByte(extension.charAt(i));
        }
        RASE.writeByte((byte) 0);
        RASE.writeInt(0);
        postorden(arbol, 0);
        RASE.seek(2 + extension.length());
        RASE.writeInt(indice);
        RASE.seek(RASE.length());
        RASE.close();
       byte[] fileContent = Files.readAllBytes(ficheroCompreso.toPath());
       this.prog.getView().añadirTexto("Comprimiendo");
        ES.setHash(hashCodigo);

        ES.Comprimir(ficheroCompreso,extension.length()+1);

        this.prog.getView().añadirTexto("Calculando entropia");
        System.out.println();
        entropia = (ES.tamComprimido() * 8) / totalFrec;
        this.prog.getView().añadirTexto("ENTROPIA  =" + entropia);
        this.prog.getView().añadirTexto("Tamaño después de compresión: "+ES.tamComprimido());
        
    }
    
    public void descomprimir(String file) {
        ES =  new EntradaSalida();
        ES.descomprimir(this.prog.getView().getPathFile());
    }
    
    private void preorden(Nodo nodo) {
        if (nodo.getHijoIzq() == null || nodo.getHijoDer() == null) {
            hashCodigo.put(nodo.getBite(), codigo);
            //imprimimos codificación Huffman
            this.prog.getView().añadirTexto("BYTE -> " +  nodo.getBite() + " Freq -> " + nodo.getFreq() + " CODIGO -> " + codigo);
            return;
        }

        codigo += "0";
        preorden(nodo.getHijoIzq()); //recorre subarbol izquierdo
        if (codigo.length() - 1 == 0) {
            codigo = "";
        } else {
            codigo = codigo.substring(0, codigo.length() - 1);
        }
        codigo += "1";
        preorden(nodo.getHijoDer()); //recorre subarbol derecho
        codigo = codigo.substring(0, codigo.length() - 1);
    }

    private void postorden(Nodo nodo, int auxiliar) throws Exception {
        if (nodo.getHijoIzq() == null || nodo.getHijoDer() == null) {
            indice++;
            RASE.writeByte((byte) indice);
            RASE.writeByte(nodo.getBite());
            RASE.writeByte((byte) -1);
            RASE.writeByte((byte) -1);
            return;
        }

        postorden(nodo.getHijoIzq(), auxiliar);
        auxiliar = indice;
        postorden(nodo.getHijoDer(), auxiliar);
        indice++;
        RASE.writeByte((byte) indice);
        RASE.writeByte(nodo.getBite());
        RASE.writeByte((byte) auxiliar);
        RASE.writeByte((byte) (indice - 1));
    }

    @Override
    public void notificar(String s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
