/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Principal.Eventos;
import Principal.Main;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class Modelo implements Eventos {

    private Main prog;
    private int dimension;
    private int posiY, posiX;
    private int posiciones[][];
    private BufferedImage imagen;
    private Image img;
    private BufferedImage imagenVacia;
    private Image imgVacia;
    private Image trozos[];
    private int multX;
    private int multY;
    private Nodo inicial;
    private Nodo aux;
    private ArrayList<Nodo> lista = new ArrayList<Nodo>();

    public Modelo(Main p) throws IOException {
        prog = p;
        this.dimension = 3;
        this.posiY = dimension - 1;
        this.posiX = dimension - 1;
        this.trozos = new Image[dimension * dimension];
        this.posiciones = new int[dimension][dimension];
        this.inicial = new Nodo(prog);
        this.aux = new Nodo(prog);
        this.initMatriz();
    }
    
    private void initMatriz() throws IOException {
        img = ImageIO.read(new File("fondo.jpg"));
        img = img.getScaledInstance(700, 700, java.awt.Image.SCALE_SMOOTH);

        imgVacia = ImageIO.read(new File("negro.jpg"));

        multX = 700 / dimension;
        multY = 700 / dimension;

        imgVacia = imgVacia.getScaledInstance(multX, multY, java.awt.Image.SCALE_SMOOTH);

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                trozos[i*dimension + j] = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(img.getSource(), new CropImageFilter(j * multY, i * multX, multX, multY)));
            }
        }

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                posiciones[i][j] = i*dimension + j;

                if (i == dimension - 1 && j == dimension - 1) {
                    posiciones[i][j] = -1;
                }
            }
        }
        
        inicial.setAnterior(null);
        inicial.setValor(-1);
        inicial.setEstado(posiciones);
        inicial.setIteracion(0);
        inicial.setPosiX(dimension - 1);
        inicial.setPosiY(dimension - 1);
    }
    
    public void mezclar(int iteraciones) {
        Random ran = new Random();
        int aux;
        int[][] pos;
        for (int i = 0; i < iteraciones; i++) {
            aux = ran.nextInt(4);
            pos = null;
            switch (aux) {
                case 0:
                    pos = movDerecha(inicial);
                    break;
                case 1:
                    pos = movAbajo(inicial);
                    break;
                case 2:
                    pos = movIzquierda(inicial);
                    break;
                case 3:
                    pos = movArriba(inicial);
                    break;
            }
            if (pos != null) {
                inicial.setEstado(pos);
            }
            //   pintar(inicial);
        }
    }
    
    public int[][] movDerecha(Nodo nodo) {
        int posiciones[][] = nodo.getEstado();
        if (nodo.getPosiX() + 1 < dimension) {
            //  System.out.println(" Puedes moverte hacia la derecha");
            int aux = posiciones[nodo.getPosiY()][nodo.getPosiX() + 1];
            posiciones[nodo.getPosiY()][nodo.getPosiX() + 1] = posiciones[nodo.getPosiY()][nodo.getPosiX()];
            posiciones[nodo.getPosiY()][nodo.getPosiX()] = aux;
            nodo.setPosiX(nodo.getPosiX() + 1);
            return posiciones;
        } else {
            // System.out.println("No puedes moverte hacia la derecha");
            return null;

        }
    }

    public int[][] movIzquierda(Nodo nodo) {
        int posiciones[][] = nodo.getEstado();
        if (nodo.getPosiX() - 1 >= 0) {
            //  System.out.println(" Puedes moverte hacia la izquierda");
            int aux = posiciones[nodo.getPosiY()][nodo.getPosiX() - 1];
            posiciones[nodo.getPosiY()][nodo.getPosiX() - 1] = posiciones[nodo.getPosiY()][nodo.getPosiX()];
            posiciones[nodo.getPosiY()][nodo.getPosiX()] = aux;
            nodo.setPosiX(nodo.getPosiX() - 1);
            return posiciones;
        } else {
            // System.out.println("No puedes moverte hacia la izquierda");
            return null;
        }
    }

    public int[][] movArriba(Nodo nodo) {
        int posiciones[][] = nodo.getEstado();
        if (nodo.getPosiY() - 1 >= 0) {
            //   System.out.println(" Puedes moverte hacia la arriba");
            int aux = posiciones[nodo.getPosiY() - 1][nodo.getPosiX()];
            posiciones[nodo.getPosiY() - 1][nodo.getPosiX()] = posiciones[nodo.getPosiY()][nodo.getPosiX()];
            posiciones[nodo.getPosiY()][nodo.getPosiX()] = aux;
            nodo.setPosiY(nodo.getPosiY() - 1);
            return posiciones;
        } else {
            //  System.out.println("No puedes moverte hacia la arriba");
            return null;
        }
    }

    public int[][] movAbajo(Nodo nodo) {
        int posiciones[][] = nodo.getEstado();
        if (nodo.getPosiY() + 1 < dimension) {
            //  System.out.println(" Puedes moverte hacia la abajo");
            int aux = posiciones[nodo.getPosiY() + 1][nodo.getPosiX()];
            posiciones[nodo.getPosiY() + 1][nodo.getPosiX()] = posiciones[nodo.getPosiY()][nodo.getPosiX()];
            posiciones[nodo.getPosiY()][nodo.getPosiX()] = aux;
            nodo.setPosiY(nodo.getPosiY() + 1);
            return posiciones;
        } else {
            //   System.out.println("No puedes moverte hacia la abajo");
            return null;
        }
    }
    
    public void añadir(Nodo nodo) {
        Nodo aux = nodo;
        while (aux != null) {
            lista.add(aux);
            aux = aux.getAnterior();
        }
        Collections.reverse(lista);
        System.out.println(lista.toString());
    }
    
     public boolean comprobar(Nodo nodo) {
        int indice = 0;
        int posiciones[][] = nodo.getEstado();
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (i != dimension - 1 || j != dimension - 1) {
                    if (posiciones[i][j] != indice) {
                        return false;
                    }
                }
                indice++;
            }
        }
        return true;
    }

    public Nodo camino() throws InterruptedException {
        System.out.println("calculando");

        ArrayList<Nodo> listaCerrada = new ArrayList<>();
        PriorityQueue<Nodo> listaAbierta = new PriorityQueue<>();

        Nodo[] vecinos;

        listaCerrada.clear();
        listaAbierta.clear();

        listaAbierta.add(inicial);
        Boolean primero = true;

        while (!listaAbierta.isEmpty()) {
            Nodo actual = listaAbierta.poll();

            //System.out.println(aa);
            if (actual.getValor() == 0) {

                return actual;
            }

            listaCerrada.add(actual);
            vecinos = conseguirVecinos(actual);

            for (Nodo vecino : vecinos) {

                if (vecino == null) {
                    continue;
                }
                conseguirValor(vecino);
                listaAbierta.add(vecino);
            }

        }

        return null;
    }

    public boolean repetido(Object[] lista, Nodo nodo) {
        Nodo aux;
        for (Object nodo1 : lista) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    aux = (Nodo) nodo1;
                    if (aux.valormatriz(i, j) != nodo.valormatriz(i, j)) {
                        return false;

                    }
                }
            }

        }

        return true;
    }

    public Nodo[] conseguirVecinos(Nodo nodo) {
        int[][] pos;
        int[][] pos2 = new int[dimension][dimension];
        Nodo[] vecinos = new Nodo[4];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                pos2[i][j] = nodo.valormatriz(i, j);
            }
        }
        Nodo aux = new Nodo(this.prog, null, pos2, nodo.getIteracion(), nodo.getPosiX(), nodo.getPosiY());
        Nodo aux2 = new Nodo(this.prog, null, pos2, nodo.getIteracion(), nodo.getPosiX(), nodo.getPosiY());

        // pintar(aux);
        pos = movAbajo(aux);
        if (pos != null) {
            vecinos[0] = new Nodo(this.prog, nodo, pos, aux.getIteracion() + 1, aux.getPosiX(), aux.getPosiY());
            ///      pintar(vecinos[0]);
        } else {
            vecinos[0] = null;
        }

        //  System.out.println("******************************");
        aux = new Nodo(this.prog, null, pos2, aux2.getIteracion(), aux2.getPosiX(), aux2.getPosiY());
        //pintar(aux);
        pos = movArriba(aux);
        if (pos != null) {
            vecinos[1] = new Nodo(this.prog, nodo, pos, aux.getIteracion() + 1, aux.getPosiX(), aux.getPosiY());
            //     pintar(vecinos[1]);
        } else {
            vecinos[1] = null;
        }

        //  System.out.println("******************************");
        aux = new Nodo(this.prog, null, pos2, aux2.getIteracion(), aux2.getPosiX(), aux2.getPosiY());
        //pintar(aux);
        pos = movDerecha(aux);
        if (pos != null) {
            vecinos[2] = new Nodo(this.prog, nodo, pos, aux.getIteracion() + 1, aux.getPosiX(), aux.getPosiY());
            //    pintar(vecinos[2]);
        } else {
            vecinos[2] = null;
        }
        //   System.out.println("******************************");

        aux = new Nodo(this.prog, null, pos2, aux2.getIteracion(), aux2.getPosiX(), aux2.getPosiY());
        //pintar(aux);
        pos = movIzquierda(aux);
        if (pos != null) {
            vecinos[3] = new Nodo(this.prog, nodo, pos, aux.getIteracion() + 1, aux.getPosiX(), aux.getPosiY());
            //   pintar(vecinos[3]);
        } else {
            vecinos[3] = null;
        }

        return vecinos;
    }

    public void conseguirValor(Nodo nodo) {

        int indice = 0;
        int valor = 0;
        int posiciones[][] = nodo.getEstado();
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (i != dimension - 1 || j != dimension - 1) {
                    if (posiciones[i][j] != indice) {
                        valor++;
                    }
                } else {
                    if (posiciones[i][j] != -1) {
                        valor++;
                    }
                }
                indice++;
            }
        }
        nodo.setValor(valor);
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) throws IOException {
        this.dimension = dimension;
        this.posiY = dimension - 1;
        this.posiX = dimension - 1;
        this.trozos = new Image[dimension * dimension];
        this.posiciones = new int[dimension][dimension];
        
        this.initMatriz();
    }

    public int[][] getPosiciones() {
        return posiciones;
    }

    public Image[] getTrozos() {
        return trozos;
    }

    public Image getImgVacia() {
        return imgVacia;
    }

    public int getMultX() {
        return multX;
    }

    public int getMultY() {
        return multY;
    }

    public Nodo getInicial() {
        return inicial;
    }

    public Nodo getAux() {
        return aux;
    }   

    public void setInicial(Nodo inicial) {
        this.inicial = inicial;
    }

    public void setAux(Nodo aux) {
        this.aux = aux;
    }
    
    @Override
    public void notificar(String s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}