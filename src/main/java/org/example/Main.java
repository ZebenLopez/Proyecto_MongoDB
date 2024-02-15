package org.example;

import org.example.services.MenuSeleccion;

import java.util.logging.Logger;

/**
 * La clase Main.
 *
 * @version 1.0  Curso 2ºA DAM Clase principal de la aplicación.
 * @autor: Zebenzuí López Conde
 */
public class Main {
    /**
     * El método principal de la aplicación.
     *
     * @param args los argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        Main main = new Main();
        main.conectar();
    }
    /**
     * Método para conectar con el menú de selección.
     */
    private void conectar() {
        MenuSeleccion.menuSeleccion();
    }
}