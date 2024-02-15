package org.example.services;

import org.example.utils.BuscarAutores;
import org.example.utils.BuscarLibros;
import org.example.utils.InsertarAutores;
import org.example.utils.InsertarLibros;

import java.util.Scanner;
/**
 * La clase MenuSeleccion.
 *
 * @version 1.0  Curso 2ºA DAM Clase para manejar las opciones de menú de la aplicación.
 * @autor: Zebenzuí López Conde
 */
@SuppressWarnings("all")
public class MenuSeleccion {
    /**
     * Método para mostrar el menú de selección y manejar las opciones del usuario.<br>
     * Las opciones incluyen insertar libros y autores (predefinidos o manualmente),
     * buscar libros por género, buscar autores por nacionalidad, y salir de la aplicación.
     */
    public static void menuSeleccion() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        System.out.println("\nBienvenido a la librería, ¿qué desea hacer?");
        while (true){
            System.out.println("\n1. Insertar libros.");
            System.out.println("2. Insertar autores.");
            System.out.println("3. Insertar libros manualmente.");
            System.out.println("4. Insertar autores manualmente.");
            System.out.println("5. Buscar libros por género.");
            System.out.println("6. Buscar autores por nacionalidad.");
            System.out.println("7. Salir.");

            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    InsertarLibros.insertarLibrosPredefinidos();
                    break;
                case 2:
                    InsertarAutores.insertarAutores();
                    break;
                case 3:
                    InsertarLibros.insertarLibrosManual();
                    break;
                case 4:
                    InsertarAutores.insertarAutoresManual();
                    break;
                case 5:
                    BuscarLibros.buscarLibrosPorGenero();
                    break;
                case 6:
                    BuscarAutores.buscarAutoresPorNacionalidad();
                    break;
                case 7:
                    System.out.println("Saliendo de la aplicación.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
    }
}