package org.example;

import org.example.utils.Conexion;

/**
 * @autor: Zebenzuí López Conde
 * @version 1.0
 * Curso 2ºA DAM
 *
 * Clase principal de la aplicación.
 */

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.conectar();
    }

    private void conectar() {
        Conexion.conectar();
    }
}