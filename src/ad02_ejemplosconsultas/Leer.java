/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ad02_ejemplosconsultas;

import java.io.BufferedReader;
import java.io.IOException;

/**
 *
 * @author jm
 */
public class Leer {
    
    private final static BufferedReader entradaConsola = 
        new java.io.BufferedReader(new java.io.InputStreamReader(System.in));

    /**
    * Leer un texto desde teclado.
    * @param mensaje Texto que se pasa al usuario
    * @return el texto introducido. Blanco en caso de error
    */
    public static String leerTexto(String mensaje) {
        String respuesta=null;
        do{
            try {
                System.out.print(mensaje);
                respuesta= entradaConsola.readLine();
            } // ()
            catch (IOException ex) {
                return "";
            }
        } while(respuesta ==null);
        return respuesta;

    } // ()


    /**
    * Introducción de numeros enteros
    * @param mensaje Mensaje que se da al usuario
    * @return un entero con el valor
    */
    public static int leerEntero(String mensaje) {
        int n=0;
        boolean conseguido=false;
        while(!conseguido){
            try{
                n= Integer.parseInt(leerTexto(mensaje));
                conseguido=true;
            }
            catch(NumberFormatException ex){
                System.err.println("Debes introducir un número correcto");
            }
        }
        return n;
    }

    /**
    * Introducción de numeros enteros
    * @param mensaje Mensaje que se da al usuario
    * @return un entero con el valor
    */
    public static double leerDouble(String mensaje) {
        double n=0.0;
        boolean conseguido=false;
        while(!conseguido){
            try{
                n= Double.parseDouble(leerTexto(mensaje));
                conseguido=true;
            }
            catch(NumberFormatException ex){
                System.out.println("Debes introducir un número correcto");
            }
        }
        return n;
    }
}