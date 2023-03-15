/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Principal.Eventos;
import Principal.Main;
import java.util.ArrayList;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class Modelo implements Eventos {

    private Main prog;
    private String text;
    private ArrayList<Nodo> palabras_erroneas;
    private ArrayList<String> palabras_propuestas;
    private Lectura lect;
    private String idioma;
    private final char[] SEPARADORES = {' ', ',', '.', ';', ':', '?', '¿', '!', '¡', '(', ')', '"'};

    public Modelo(Main p) {
        prog = p;
        this.palabras_erroneas = new ArrayList<>();
        this.palabras_propuestas = new ArrayList<>();
    }

    public int detectarErrores() {
        //Declaraciones
        String palabra = "";

        //Instrucciones
        this.setText();

        text = text.toLowerCase(); // o se hace antes;
        for (int i = 0; i <= text.length(); i++) {
            if (i != text.length() && !this.isSepChar(text.charAt(i))) { //VIGILA!!!
                palabra += text.charAt(i);
            } else if (!"".equals(palabra)){
                lect = new Lectura(idioma);
                lect.openFile();
                analizarPalabra(lect, palabra, i);
                lect.closeFile();
                palabra = "";
            }
        }
        return this.palabras_erroneas.size();
    }

    public void corregir() {
        Nodo aux;
        for (int i = palabras_erroneas.size() - 1; i >= 0; i--) {
            aux = palabras_erroneas.get(i);
            this.prog.getView().replace(aux.getPalabra(), aux.getFirstProp(), aux.getIndex());
        }
        this.palabras_erroneas.clear();
        this.palabras_propuestas.clear();
    }

    public void detectarIdioma() {
        //Declaraciones
        String palabra = "";

        //Instrucciones
        this.setText();

        text = text.toLowerCase(); // o se hace antes;
        double costeCastellano = 0, costeingles = 0, costecatala = 0;
        // double aux1 = 0, aux2 = 0, aux3 = 0;
        for (int i = 0, indice = 0; i <= text.length() && indice < 10; i++) {
            if (i != text.length() && !this.isSepChar(text.charAt(i))) { //VIGILA!!!
                palabra += text.charAt(i);
            } else if (!"".equals(palabra)){
                lect = new Lectura("catalan.dic");
                lect.openFile();
                costecatala += analizarCoste(lect, palabra);
                lect.closeFile();

                lect = new Lectura("castellano.dic");
                lect.openFile();
                lect.openFile();
                costeCastellano += analizarCoste(lect, palabra);
                lect.closeFile();

                lect = new Lectura("ingles.dic");
                lect.openFile();
                lect.openFile();
                costeingles += analizarCoste(lect, palabra);
                lect.closeFile();

                indice++;

                palabra = "";
            }
        }
        if (costeCastellano < costecatala) {
            if (costeCastellano < costeingles) {
                this.idioma = "castellano.dic";
            } else {
                this.idioma = "ingles.dic";
            }
        } else {
            if (costecatala < costeingles) {
                this.idioma = "catalan.dic";
            } else {
                this.idioma = "ingles.dic";
            }
        }
    }
    
    public void cleanCache() {
        this.palabras_erroneas.clear();
        this.palabras_propuestas.clear();
        this.idioma = "";
        this.text = "";
    }

    public ArrayList<Nodo> getPalabras_erroneas() {
        return palabras_erroneas;
    }   
    
    public boolean isErronea(String pal) {
        for (int i = 0; i < palabras_erroneas.size(); i++) {
            if (pal.equals(palabras_erroneas.get(i).getPalabra())) {
                return true;
            }
        }
        return false;
    }
    
    public ArrayList<String> getPalabras_propuestas(String pal) {
        for (int i = 0; i < palabras_erroneas.size(); i++) {
            if (pal.equals(palabras_erroneas.get(i).getPalabra())) {
                return palabras_erroneas.get(i).getPalabras_propuestas();
            }
        }
        return null;
    }
    
    public void removePalabraErronea(String pal, int index) {
        for (int i = 0; i < palabras_erroneas.size(); i++) {
            if (pal.equals(palabras_erroneas.get(i).getPalabra()) && index == palabras_erroneas.get(i).getIndex()) {
                palabras_erroneas.remove(i);
            }
        }
    }
    
    private boolean isSepChar(char c) {
        for (int i = 0; i < SEPARADORES.length; i++) {
            if (SEPARADORES[i] == c) {
                return true;
            }
        }
        return false;
    }

    private int analizarPalabra(Lectura lect, String pal, int index) {
        Nodo nodo;
        String word;
        int coste = -1;
        int min = 99;

        while (lect.isNotEOF()) {
            word = lect.readPalabra();
            coste = distanciaLevenshtein(pal.toCharArray(), word.toCharArray());
            if (coste == 0) {
                break;
            } else if (coste < min) {
                min = coste;
                palabras_propuestas.clear();
                palabras_propuestas.add(word);
            } else if (coste == min) {
                palabras_propuestas.add(word);
            }
        }
        if (coste != 0) {
            nodo = new Nodo(pal);
            nodo.setIndex(index);
            nodo.añadir_Lista(palabras_propuestas);
            palabras_erroneas.add(nodo);
        }
        palabras_propuestas.clear();
        return min;
    }

    private void setText() {
        this.text = this.prog.getView().getText();
    }

    private int analizarCoste(Lectura lect, String pal) {
        Nodo nodo;
        String word;
        int coste = -1;
        int min = 99;

        while (lect.isNotEOF()) {
            word = lect.readPalabra();
            coste = distanciaLevenshtein(pal.toCharArray(), word.toCharArray());
            if (coste == 0) {
                min = coste;
                break;
            } else if (coste < min) {
                min = coste;
            }
        }
        return min;
    }

    private int distanciaLevenshtein(char[] palabra, char[] palabra_leida) {
        int coste = 0;
        int aux_min = 0;
        int[][] distancia = new int[palabra.length + 1][palabra_leida.length + 1];

        for (int i = 0; i < palabra.length + 1; i++) {
            distancia[i][0] = i;
        }

        for (int j = 0; j < palabra_leida.length + 1; j++) {
            distancia[0][j] = j;
        }

        for (int i = 1; i < palabra.length + 1; i++) {
            for (int j = 1; j < palabra_leida.length + 1; j++) {
                if (palabra[i - 1] == palabra_leida[j - 1]) {
                    coste = 0;
                } else {
                    coste = 1;
                }
                aux_min = Math.min(distancia[i - 1][j] + 1, distancia[i][j - 1] + 1);
                distancia[i][j] = Math.min(aux_min, distancia[i - 1][j - 1] + coste);
            }
        }
        return distancia[palabra.length][palabra_leida.length];
    }

    @Override
    public void notificar(String s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
