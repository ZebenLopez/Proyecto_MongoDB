package org.example.utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.example.services.Conexion;

import java.util.Scanner;
import java.util.HashSet;
import java.util.Set;
/**
 * La clase BuscarLibros.
 *
 * @version 1.0  Curso 2ºA DAM Clase para buscar libros por género en la base de datos.
 * @autor: Zebenzuí López Conde
 */
@SuppressWarnings("all")
public class BuscarLibros {
    /**
     * Método para buscar libros por género en la base de datos. <br>
     * Muestra los géneros disponibles, pide al usuario que introduzca un género,
     * y luego muestra todos los libros de ese género.
     */
    public static void buscarLibrosPorGenero() {
        try (MongoClient mongoClient = Conexion.conectar()) {
            MongoDatabase database = mongoClient.getDatabase("Libreria");
            MongoCollection<Document> collection = database.getCollection("Libros");

            // Mostrar los géneros disponibles
            Set<String> generos = new HashSet<>();
            try (MongoCursor<Document> cursor = collection.find().iterator()) {
                while (cursor.hasNext()) {
                    Document document = cursor.next();
                    generos.add(document.getString("genero"));
                }
            }
            System.out.println("\nGéneros disponibles: " + generos + "\n");

            // Preguntar al usuario
            Scanner scanner = new Scanner(System.in);
            System.out.println("Introduce el género del libro:");
            String genero = scanner.nextLine().toLowerCase();

            MongoCursor<Document> cursor = collection.find().iterator();

            boolean found = false;
            while (cursor.hasNext()) {
                Document document = cursor.next();
                if (document.getString("genero").toLowerCase().equals(genero)) {
                    if (!found) {
                        System.out.println("\n--> Libros del género " + genero + ":\n");
                        found = true;
                    }
                    System.out.println("Título: " + document.getString("titulo"));
                    System.out.println("Autor: " + document.getString("autor"));
                    System.out.println("Año de publicación: " + ((Integer) document.getDouble("anio_publicacion").intValue()));
                    System.out.println("Idioma: " + document.getString("idioma"));
                    System.out.println("-------------------------");
                }
            }

            if (!found) {
                System.out.println("No hay libros del género " + genero + " en la base de datos.");
            }
        }
    }
}