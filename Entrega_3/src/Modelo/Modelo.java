/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Principal.Main;
import Principal.Eventos;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class Modelo implements Eventos {

    private Main prog;
    private ArrayList<Integer> num1 = new ArrayList<>();
    private ArrayList<Integer> num2 = new ArrayList<>();
    
    private int numeroCorte = 2;
    private int numeroPruebas = 1000;
    private double constante = 0;

    public Modelo(Main p) {
        prog = p;
    }

    public void setNums() {
        String aux1 = this.prog.getView().getNum1();
        String aux2 = this.prog.getView().getNum2();
        
        this.num1.clear();
        this.num2.clear();
        
        for (int i = 0; i < aux1.length(); i++) {
            this.num1.add((int) aux1.charAt(i) - 48);
        }
        for (int i = 0; i < aux2.length(); i++) {
            this.num2.add((int) aux2.charAt(i) - 48);
        }
        
        Collections.reverse(this.num1);
        Collections.reverse(this.num2);
    }  
    
    public void runKaratsuba() {
        ArrayList<Integer> resultado = karatsuba(this.num1, this.num2);
        Collections.reverse(resultado);
        this.prog.getView().añadirTexto(resultado.toString().replace(", ", ""));
    }
    
     public void runNormal() {
        ArrayList<Integer> resultado = multiplicacionNormal(this.num1, this.num2);
        Collections.reverse(resultado);
        this.prog.getView().añadirTexto(resultado.toString().replace(", ", ""));
    }
     
      public void runMixto() throws IOException {
        ArrayList<Integer> resultado = algoritmoMixto(this.num1, this.num2);
        Collections.reverse(resultado);
        this.prog.getView().añadirTexto(resultado.toString().replace(", ", ""));
    }
    
    public ArrayList<Integer> karatsuba(ArrayList<Integer> i, ArrayList<Integer> j) {

        ArrayList<Integer> auxiliar;
        ArrayList<Integer> a;
        ArrayList<Integer> b;
        ArrayList<Integer> c;
        ArrayList<Integer> d;

        ArrayList<Integer> primero;
        ArrayList<Integer> segundo;
        ArrayList<Integer> tercero;

        ArrayList<Integer> particion1;
        ArrayList<Integer> particion2;

        int n;

        if (i.size() < 2 || j.size() < 2) {
            auxiliar = multiplicacionPequeñaKaratsuba(i, j);
            return auxiliar;
        }

        n = Math.min(i.size(), j.size());
        n = (int) Math.floor(n / 2);

        b = new ArrayList<>(i.subList(0, n));

        d = new ArrayList<>(j.subList(0, n));

        a = new ArrayList<>(i.subList(n, i.size()));

        c = new ArrayList<>(j.subList(n, j.size()));

        primero = karatsuba(a, c);
        segundo = karatsuba(b, d);
        tercero = karatsuba(suma(a, b), suma(c, d));
        
        particion1 = suma(multiplicacion(resta(resta(tercero, primero), segundo), n), segundo);

        particion2 = multiplicacion(primero, n * 2);

        return suma(particion2, particion1);
    }
    
    public ArrayList<Integer> multiplicacionNormal(ArrayList<Integer> numero1, ArrayList<Integer> numero2) {

        int acarreo = 0;
        int indice = -1;
        int aux;
        int auxiliar1;
        int auxiliar2;
        int auxiliar3;
        int posicion;
        ArrayList<Integer> num1;
        ArrayList<Integer> num2;
        ArrayList<Object[]> presuma = new ArrayList<>();
        ArrayList<Integer> multiplicacion = new ArrayList<>();
        ArrayList<Integer> auxiliar = new ArrayList<>();
        ArrayList<Integer> solucion = new ArrayList<>();

        if (numero1.size() < numero2.size()) {//Aqui lo que hago es ver que elemento es mas grande, por si acaso pq no se si se puede dar este caso, para que el algoritmo sea el mismo
            num1 = numero2;
            num2 = numero1;

        } else {
            num1 = numero1;
            num2 = numero2;
        }

        for (int i = 0; i < num2.size(); i++) {
            for (int j = 0; j < num1.size(); j++) {
                aux = num2.get(i) * num1.get(j);
                aux = aux + acarreo;
                acarreo = (int) aux / 10;
                multiplicacion.add(aux % 10);
            }
            if (acarreo != 0) {
                multiplicacion.add(acarreo);
                acarreo = 0;
            }
            presuma.add(multiplicacion.toArray());
            indice++;
            if (presuma.size() == 2) {
                auxiliar1 = presuma.get(0).length;
                auxiliar2 = presuma.get(1).length;
                posicion = auxiliar2 + indice;
                for (int j = 0; j < posicion; j++) {
                    if (indice > j) {
                        auxiliar.add((int) presuma.get(0)[j]);
                    } else {
                        if (j < auxiliar1) {
                            auxiliar3 = (int) presuma.get(0)[j] + (int) presuma.get(1)[j - indice] + acarreo;
                            acarreo = (int) auxiliar3 / 10;
                            auxiliar.add(auxiliar3 % 10);
                        } else {
                            auxiliar3 = (int) presuma.get(1)[j - indice] + acarreo;
                            acarreo = (int) auxiliar3 / 10;
                            auxiliar.add(auxiliar3 % 10);
                        }
                    }
                }
                if (acarreo != 0) {
                    auxiliar.add(acarreo);
                    acarreo = 0;
                }
                presuma.clear();
                presuma.add(auxiliar.toArray());
                auxiliar.clear();
            }
            multiplicacion.clear();
        }
        for (int i = 0; i < presuma.get(0).length; i++) {
            solucion.add((Integer) presuma.get(0)[i]);
        }
        while (solucion.get(solucion.size() - 1) == 0 && solucion.size() != 1) {
            //solucion.remove(solucion.size() - 1);
            solucion = new ArrayList<>(solucion.subList(0, solucion.size() - 1));
        }
        return solucion;
    }
    
    /**************************************************************************/
    
    private ArrayList<Integer> multiplicacionPequeñaKaratsuba(ArrayList<Integer> i, ArrayList<Integer> j) {

        ArrayList<Integer> solucion = new ArrayList<>();
        ArrayList<Integer> numero1;
        ArrayList<Integer> numero2;
        int acarreo = 0;
        int auxiliar3 = 0;

        if (i.size() < j.size()) {//Aqui lo que hago es ver que elemento es mas grande, por si acaso pq no se si se puede dar este caso, para que el algoritmo sea el mismo
            numero1 = new ArrayList<>(j);
            numero2 = new ArrayList<>(i);

        } else {
            numero1 = new ArrayList<>(i);
            numero2 = new ArrayList<>(j);
        }

        for (int x = 0; x < numero1.size(); x++) {
            auxiliar3 = numero1.get(x) * numero2.get(0) + acarreo;
            acarreo = (int) auxiliar3 / 10;
            solucion.add(auxiliar3 % 10);
        }
        if (acarreo != 0) {
            solucion.add(acarreo);
            acarreo = 0;
        }
        while (solucion.get(solucion.size() - 1) == 0 && solucion.size() != 1) {
            //solucion.remove(solucion.size() - 1);
            solucion = new ArrayList<>(solucion.subList(0, solucion.size() - 1));
        }
        return solucion;
    }
    
    private ArrayList<Integer> suma(ArrayList<Integer> i, ArrayList<Integer> j) {//Suma de dos numeros en un array

        boolean auxiliar2;
        boolean cambio = false;
        ArrayList<Integer> solucion = new ArrayList<>();
        ArrayList<Integer> numero1;
        ArrayList<Integer> numero2;
        boolean negativo1, negativo2;

        if (i.get(i.size() - 1) == -1) {//Miramos si el primer numero es negativo y le quitamos el signo
            negativo1 = true;
            // i.remove(0);
            i = new ArrayList<>(i.subList(0, i.size() - 1));
        } else {
            negativo1 = false;
        }
        if (j.get(j.size() - 1) == -1) {//Miramos si el segundo numero es negativo y le quitamos el signo
            negativo2 = true;
            //j.remove(0);
            j = new ArrayList<>(j.subList(0, j.size() - 1));
        } else {
            negativo2 = false;
        }

        if (i.size() < j.size()) {//Aqui lo que hago es ver que elemento es mas grande, por si acaso pq no se si se puede dar este caso, para que el algoritmo sea el mismo
            numero1 = new ArrayList<>(j);
            numero2 = new ArrayList<>(i);
            cambio = true;

        } else {
            numero1 = new ArrayList<>(i);
            numero2 = new ArrayList<>(j);
        }

        if ((negativo1 && negativo2)) {//Caso en el que los 2 numeros son negativos
            solucion = operacionSuma(numero1, numero2);
            solucion.add(-1);
        }

        if ((!negativo1 && !negativo2)) {//Caso en el que los 2 numeros son positivos
            solucion = operacionSuma(numero1, numero2);

        }

        if ((negativo1 && !negativo2)) {//Caso en el que el primer numero es positivo y el segundo es negativo
            solucion = resta(numero1, numero2);
            if (!cambio) {
                solucion.add(-1);
            }
        }

        if ((!negativo1 && negativo2)) {//Caso en el que el primer numero es positivo y el segunddo es negativo
            solucion = resta(numero1, numero2);
            if (cambio) {
                solucion.add(-1);
            }
        }

        return solucion;
    }

    private ArrayList<Integer> resta(ArrayList<Integer> i, ArrayList<Integer> j) {

        boolean cambio = false;

        ArrayList<Integer> solucion = new ArrayList<>();
        ArrayList<Integer> numero1;
        ArrayList<Integer> numero2;
        boolean negativo1;
        boolean negativo2;
        boolean fin = false;
        boolean numeroigual = true;

        if (i.get(i.size() - 1) == -1) {//Miramos si el primer numero es negativo y le quitamos el signo
            negativo1 = true;
            //i.remove(0);
            i = new ArrayList<>(i.subList(0, i.size() - 1));
        } else {
            negativo1 = false;
        }
        if (j.get(j.size() - 1) == -1) {//Miramos si el segundo numero es negativo y le quitamos el signo
            negativo2 = true;
            //j.remove(0);
            j = new ArrayList<>(j.subList(0, j.size() - 1));
        } else {
            negativo2 = false;
        }

        if (i.size() != j.size()) {//Si los tamaños de los array son diferentes

            if (i.size() < j.size()) {//Ahora miramos que tamaño tienen los array para poder decir cual es numero 1 y cual el nuimero2 

                cambio = true;
                numero1 = new ArrayList<>(j);
                numero2 = new ArrayList<>(i);

            } else {
                numero1 = new ArrayList<>(i);
                numero2 = new ArrayList<>(j);
            }

        } else {

            for (int k = i.size() - 1; k > 0 && !fin; k--) {
                if (i.get(k) > j.get(k)) {
                    fin = true;
                    numeroigual = true;
                }
                if (i.get(k) < j.get(k)) {
                    fin = true;
                    numeroigual = false;
                }
            }

            if (numeroigual) {
                numero1 = new ArrayList<>(i);
                numero2 = new ArrayList<>(j);

            } else {
                numero1 = new ArrayList<>(j);
                numero2 = new ArrayList<>(i);
                cambio = true;

            }
        }

        if ((negativo1 && negativo2)) {//Caso en el que los 2 numeros son negativos
            solucion = operacionResta(numero1, numero2);
            if (!cambio) {//Si no ha habido cambio de numeros cambiamos el signo
                solucion.add(-1);
            }
        }

        if ((!negativo1 && !negativo2)) {//Caso en el que los 2 numeros son positivos
            solucion = operacionResta(numero1, numero2);
            if (cambio) {
                solucion.add(-1);
            }
        }

        if ((negativo1 && !negativo2)) {//Caso en el que el primer numero es positivo y el segundo es negativo
            solucion = operacionSuma(numero1, numero2);
            solucion.add(-1);
        }

        if ((!negativo1 && negativo2)) {//Caso en el que el primer numero es positivo y el segunddo es negativo
            solucion = operacionSuma(numero1, numero2);
        }

        return solucion;
    }
    
    private ArrayList<Integer> operacionResta(ArrayList<Integer> numero1, ArrayList<Integer> numero2) {

        int auxiliar3;
        int acarreo = 0;
        ArrayList<Integer> solucion = new ArrayList<>();

        for (int x = 0; x < numero1.size(); x++) {
            if (x < numero2.size()) {
                auxiliar3 = numero1.get(x) - acarreo;
                if (auxiliar3 >= numero2.get(x)) {
                    auxiliar3 = auxiliar3 - numero2.get(x);
                    acarreo = 0;
                    solucion.add(auxiliar3);
                } else {
                    acarreo = 1;
                    auxiliar3 = auxiliar3 + 10;
                    auxiliar3 = auxiliar3 - numero2.get(x);
                    solucion.add(auxiliar3);
                }
            } else {
                auxiliar3 = numero1.get(x) - acarreo;
                acarreo = 0;
                solucion.add(auxiliar3);
            }
        }

        while (solucion.get(solucion.size() - 1) == 0 && solucion.size() != 1) {
            //solucion.remove(solucion.size() - 1);
            solucion = new ArrayList<>(solucion.subList(0, solucion.size() - 1));
        }

        return solucion;
    }

    private ArrayList<Integer> operacionSuma(ArrayList<Integer> numero1, ArrayList<Integer> numero2) {
        int auxiliar3;
        int acarreo = 0;
        ArrayList<Integer> solucion = new ArrayList<>();

        for (int x = 0; x < numero1.size(); x++) {

            if (x < numero2.size()) {
                auxiliar3 = numero1.get(x) + numero2.get(x) + acarreo;
                acarreo = (int) auxiliar3 / 10;
                solucion.add(auxiliar3 % 10);
            } else {

                auxiliar3 = numero1.get(x) + acarreo;
                acarreo = (int) auxiliar3 / 10;
                solucion.add(auxiliar3 % 10);
            }

        }
        if (acarreo != 0) {
            solucion.add(acarreo);
            acarreo = 0;
        }

        while (solucion.get(solucion.size() - 1) == 0 && solucion.size() != 1) {
            //solucion.remove(solucion.size() - 1);
            solucion = new ArrayList<>(solucion.subList(0, solucion.size() - 1));
        }
        return solucion;
    }
    
    private ArrayList<Integer> multiplicacion(ArrayList<Integer> i, int j) {//No hace mulyiplicar array ya que nunca se hace, solo multiplicac por 10 elevado a algo, que es lo mismo qie poner ceros en el array 
        boolean aux = false;
        if (i.get(i.size() - 1) == -1) {//Miramos si el primer numero es negativo y le quitamos el signo
            //i.remove(0);
            i = new ArrayList<>(i.subList(0, i.size() - 1));
            aux = true;
        }
        Collections.reverse(i);//Esto es porque cuando haces el add con 2 parametros es mas cosatoso el add con 1 parametro
        for (int k = 0; k < j; k++) {
            i.add(0);
        }
        Collections.reverse(i);
        if (aux) {
            i.add(-1);
        }
        return i;
    }
    
    public ArrayList<Integer> algoritmoMixto(ArrayList<Integer> i, ArrayList<Integer> j) {
        ArrayList<Integer> auxiliar;
        ArrayList<Integer> a;
        ArrayList<Integer> b;
        ArrayList<Integer> c;
        ArrayList<Integer> d;

        ArrayList<Integer> primero;
        ArrayList<Integer> segundo;
        ArrayList<Integer> tercero;

        ArrayList<Integer> particion1;
        ArrayList<Integer> particion2;

        int n;

        if (i.size() < numeroCorte || j.size() < numeroCorte) {
            auxiliar = multiplicacionNormal(i, j);
            return auxiliar;
        }

        n = Math.min(i.size(), j.size());
        n = (int) Math.floor(n / 2);

        b = new ArrayList<>(i.subList(0, n));

        d = new ArrayList<>(j.subList(0, n));

        a = new ArrayList<>(i.subList(n, i.size()));

        c = new ArrayList<>(j.subList(n, j.size()));

        primero = algoritmoMixto(a, c);
        segundo = algoritmoMixto(b, d);
        tercero = algoritmoMixto(suma(a, b), suma(c, d));

        particion1 = suma(multiplicacion(resta(resta(tercero, primero), segundo), n), segundo);

        particion2 = multiplicacion(primero, n * 2);

        return suma(particion2, particion1);
    }
    
    public ArrayList<Integer> toArrayList(String num) {
        String[] numero = num.split("");
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = numero.length - 1; i >= 0; i--) {

            res.add(Integer.parseInt(numero[i]));
        }
        return res;
    }
    
    public void calculoCorte() {

        long tiempo;
        double mediaKaratusba = 0, constanteKaratsuba = 0;
        double mediaNormal = 0, constanteNormal = 0;
        String numero1 = "";
        String numero2 = "";
        ArrayList<Integer> num1;
        ArrayList<Integer> num2;
        Random ran = new Random();

        for (int i = 0; i < numeroPruebas; i++) {
            numero1 = numero1 + String.valueOf(ran.nextInt(9));
        }
        for (int i = 0; i < numeroPruebas; i++) {
            numero2 = numero2 + String.valueOf(ran.nextInt(9));
        }
        num1 = toArrayList(numero1);
        num2 = toArrayList(numero2);

        Collections.reverse(num1);
        Collections.reverse(num2);

        for (int k = 0; k < 5; k++) {
            tiempo = System.currentTimeMillis();
            karatsuba(num2, num1);
            tiempo = System.currentTimeMillis() - tiempo;
            mediaKaratusba += tiempo;
        }

        for (int k = 0; k < 5; k++) {
            tiempo = System.currentTimeMillis();
            multiplicacionNormal(num1, num2);
            tiempo = System.currentTimeMillis() - tiempo;
            mediaNormal += tiempo;
        }

        mediaKaratusba = mediaKaratusba / 5;
        mediaNormal = mediaNormal / 5;

        constanteKaratsuba = mediaKaratusba / Math.pow(numeroPruebas, ((Math.log(3.0) / Math.log(2.0))));
        constanteNormal = mediaNormal / Math.pow(numeroPruebas, 2);

        constante = (constanteKaratsuba / constanteNormal);
        numeroCorte = (int) bolzano(1, 1000000000);
    }

    public double bolzano(double a, double b) {
        double m = (a + b) / 2;
        double vm = funcio(m);
        double vb = funcio(b);
        if (Math.abs(vm) < 0.0000001) {
            return m;
        } else {
            if (((vb > 0) && (vm > 0)) || ((vb < 0) && (vm < 0))) {
                return bolzano(a, m);
            } else {
                return bolzano(m, b);
            }
        }
    }

    public double funcio(double n) {
        double res;
        res = (Math.pow(n, 2) / Math.pow(n, ((Math.log(3.0) / Math.log(2.0))))) - constante;
        return res;
    }
    
    public int getNumeroCorte() {
        return this.numeroCorte;
    }
    
    public void setNumeroCorte(int n) {
        this.numeroCorte = n;
    }
    
    @Override
    public void notificar(String s) {
        if (s.startsWith("Normal")) {
            this.runNormal();
        } else if (s.startsWith("Karatsuba")) {
            this.runKaratsuba();
        } else if (s.startsWith("Mixto")) {
            try {
                this.runMixto();
            } catch (IOException ex) {
                Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (s.startsWith("SetNums")) {
            this.setNums();
        }
    }
}
