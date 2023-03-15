/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Colores.Paleta;
import Principal.Eventos;
import Principal.Main;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class Modelo implements Eventos {

    private Main prog;
    private Paleta paleta;
    private HashMap<String, Integer> colores; //map variable <Nombre-color, Num.pixeles>
    private HashMap<String, Bandera> banderasBD; //hash para cada bandera en la BD y que contenga los puntos
    private ArrayList<String> paises;
    private Random rdm;
    private Escritura esc;
    private Lectura lec;
    private BufferedImage imagenElegida;
    private HashMap<String, Integer> hashSolucion;
    private ArrayList<Nodo> solucionFinal;
    private final int pixelesMuestreo = 1000; 
    private final int numIteraciones = 10;
    private final double margen = 5.00;
    
    private int totBar = 0;

    public Modelo(Main p) {
        prog = p;
        rdm = new Random();
        paleta = new Paleta();

        this.colores = new HashMap<>();
        colores.put("Blanco", 0);
        colores.put("Negro", 0);
        colores.put("Rojo", 0);
        colores.put("Verde", 0);
        colores.put("Azul", 0);
        colores.put("Amarillo", 0);
        colores.put("Naranja", 0);
        paises = new ArrayList<>();
    }

    // Procesar la imagen leída para devolver bandera con estimación del % de colores a partir de N pixeles
    public Bandera procesarImagenBandera(BufferedImage img, int pixelesMuestreo) {

        String colorName;
        int ladoXCentro = (img.getWidth() );
        int ladoYCentro = (img.getHeight());
        int yCentral = (img.getHeight() / 2) - (ladoYCentro / 2);
        int xCentral = (img.getWidth() / 2) - (ladoXCentro / 2);

        int pixelX, pixelY;
        Color col;

        boolean hasAlpha = img.getAlphaRaster() != null;

        for (int i = pixelesMuestreo; i > 0; i--) {
            pixelX = (int) (xCentral + ((double) (ladoXCentro) * rdm.nextDouble()));
            pixelY = (int) (yCentral + ((double) (ladoYCentro) * rdm.nextDouble()));

            col = new Color(img.getRGB(pixelX, pixelY), hasAlpha);

            if (col.getAlpha() != 0) { //si no es totalmente transparente
                colorName = paleta.getNombre(paleta.analizarColor(col));
                colores.put(colorName, colores.get(colorName) + 1);
            } else {
                i++;
            }

        }

        int value;
        Bandera bandera = new Bandera();

        for (Map.Entry<String, Integer> entry : colores.entrySet()) {
            value = entry.getValue();
            //ponemos porcentaje
            double aux = (double) value / pixelesMuestreo;
            bandera.putColorValue(entry.getKey(), aux * 100.00);
        }

        colores.entrySet().forEach((entry) -> {
            entry.setValue(0);
        });

        return bandera;
    }

    //Comparar los colores de la bandera por cada bandera en la BD.
    public ArrayList<String> getNombreBanderaDeImagen(Bandera banderaIMG) {

        paises.clear();
        Bandera banderaBD;
        double valueIMG, valueBD;
        int maxPoints = -1;
        for (Map.Entry<String, Bandera> banderaIter : banderasBD.entrySet()) {
            banderaBD = banderaIter.getValue();

            for (Map.Entry<String, Double> colorIter : banderaBD.getPaleta().entrySet()) {
                valueIMG = banderaIMG.getColorValue(colorIter.getKey());
                valueBD = colorIter.getValue();
                if (valueIMG - valueBD >= -margen && valueIMG - valueBD <= margen) {
                    //suma punto en banderBD
                    banderaBD.addPoint();
                }
            }
            //calcula maxpoint aquí y cargar países candidatos
            if (banderaBD.getPoints() > maxPoints) {
                paises.clear();
                maxPoints = banderaBD.getPoints();
                paises.add(banderaBD.getNombrePais());
            } else if (banderaBD.getPoints() == maxPoints) {
                paises.add(banderaBD.getNombrePais());
            };
        }

        return paises;
    }

    public Bandera procesarBD(BufferedImage img, String fichero) {
        int width = img.getWidth();
        int height = img.getHeight();
        String colorName;

        Bandera bandera = new Bandera(fichero);

        //algoritmo tradicional de pixelado
        for (int pixelX = 0; pixelX < width; pixelX++) {
            for (int pixelY = 0; pixelY < height; pixelY++) {
                colorName = paleta.getNombre(paleta.analizarColor(new Color(img.getRGB(pixelX, pixelY))));
                colores.put(colorName, colores.get(colorName) + 1);
            }
        }

        int area = width * height;
        int value;

        for (Map.Entry<String, Integer> entry : colores.entrySet()) {
            value = entry.getValue();
            //ponemos porcentaje
            bandera.putColorValue(entry.getKey(), (double) (value) / (double) (area) * 100.00);
        }

        colores.entrySet().forEach((entry) -> {
            entry.setValue(0);
        });

        return bandera;

    }

    public void grabarBD(Bandera bandera) {
        try {
            esc.writeObject(bandera);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // cargamos banderas en banderasBD mientras se crea
    public void crearBD(String fileBD) {

        try {

            String base = "flags/";

            BufferedImage bfImage;
            File dir = new File(base);
            String[] ficheros = dir.list();
            totBar = ficheros.length;
            prog.getView().progressBar();
            Bandera bandera;
            esc = new Escritura(fileBD);
            banderasBD = new HashMap<>();
            int percentBar = 0;
            for (String fichero : ficheros) {
                bfImage = ImageIO.read(new File(base + fichero));
                bandera = procesarBD(bfImage, fichero);
                grabarBD(bandera); //escribe bandera en fichero;
                banderasBD.put(bandera.getNombrePais(), bandera); //cargamos en BD a la vez
                percentBar++;
                prog.getView().getPb().actualizarBarra(percentBar);
            }
            prog.getView().getPb().setVisible(false);
            //escribir bandera centinela
            esc.writeObject(new Bandera("X"));
            esc.closeFile();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void cargarBD(String fileBD) {

        try {
            lec = new Lectura(fileBD);
            if (banderasBD == null) {
                banderasBD = new HashMap<>();
            } else {
                banderasBD.clear();
            }

            Bandera bandera = (Bandera) lec.readObject();
            while (!bandera.getNombrePais().equals("X")) {
                banderasBD.put(bandera.getNombrePais(), bandera);
                bandera = (Bandera) lec.readObject();
            }
            lec.close();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());

        }

    }

    //función que permite ver diferentes banderas seleccionadas
    public void deteccionBandera() {

        Bandera bandera;
        ArrayList<String> paisesSimilares;
        String nombre = "";
        int valor = 0;
        hashSolucion = new HashMap<>();
        solucionFinal = new ArrayList<>();
        int maxpoint = 0;

        //algoritmo Montecarlo -> 5 veces
        for (int i = 0; i < numIteraciones; i++) {
            //se genera bandera con algoritmo numérico analizando 500 píxeles
            bandera = procesarImagenBandera(imagenElegida, pixelesMuestreo);
            //se obtiene lista de países candidatos
            paisesSimilares = getNombreBanderaDeImagen(bandera);
            //sepuntúa repetición de países
            for (int j = 0; j < paisesSimilares.size(); j++) {
                nombre = paisesSimilares.get(j);
                if (hashSolucion.containsKey(nombre)) {
                    valor = hashSolucion.get(nombre) + 1;
                    hashSolucion.put(nombre, valor);
                } else {
                    hashSolucion.put(nombre, 0);
                }
                if (valor > maxpoint) {
                    maxpoint = valor;
                }
            }
        }

        //se genera lista de países candidatos en orden descendiente para pintar en la vista
        for (Map.Entry<String, Integer> iterador : hashSolucion.entrySet()) {

            if (maxpoint == iterador.getValue()) {
                Nodo nodo = new Nodo(iterador.getKey(), iterador.getValue());
                solucionFinal.add(nodo);
            }
        }

        banderasBD.entrySet().forEach((entry) -> {
            entry.getValue().setPoints(0);
        });

        prog.getView().notificar("Pintar");

    }

    public String getNombre(int i) {

        return solucionFinal.get(i).getNombre();
    }

    public int getLonguitud() {
        return solucionFinal.size();
    }

    @Override
    public void notificar(String s) {
        throw new UnsupportedOperationException("Not supported yet.");

    }

    public void setImagenElegida(BufferedImage imagenElegida) {
        this.imagenElegida = imagenElegida;
    }


    public int getTotBar() {
        return totBar;
    }

    
}
