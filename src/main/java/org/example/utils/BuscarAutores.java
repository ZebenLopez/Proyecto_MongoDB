package org.example.utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.example.services.Conexion;

import java.util.Scanner;import java.util.HashSet;
import java.util.Set;
/**
 * La clase BuscarAutores.
 *
 * @version 1.0  Curso 2ºA DAM Clase para buscar autores por nacionalidad en la base de datos.
 * @autor: Zebenzuí López Conde
 */
@SuppressWarnings("all")
public class BuscarAutores {
    /**
     * Método para buscar autores por nacionalidad en la base de datos.<br>
     * Muestra las nacionalidades disponibles, pide al usuario que introduzca una nacionalidad,
     * y luego muestra todos los autores de esa nacionalidad.
     */
    public static void buscarAutoresPorNacionalidad() {
        try (MongoClient mongoClient = Conexion.conectar()) {
            MongoDatabase database = mongoClient.getDatabase("Libreria");
            MongoCollection<Document> collection = database.getCollection("Autores");

            // Mostrar las nacionalidades disponibles
            Set<String> nacionalidades = new HashSet<>();
            try (MongoCursor<Document> cursor = collection.find().iterator()) {
                while (cursor.hasNext()) {
                    Document document = cursor.next();
                    nacionalidades.add(document.getString("nacionalidad"));
                }
            }
            System.out.println("\nNacionalidades disponibles: " + nacionalidades + "\n");

            // Preguntar al usuario
            Scanner scanner = new Scanner(System.in);
            System.out.println("Introduce la nacionalidad del autor:");
            String nacionalidad = scanner.nextLine().toLowerCase();

            MongoCursor<Document> cursor = collection.find().iterator();

            boolean found = false;
            while (cursor.hasNext()) {
                Document document = cursor.next();
                if (document.getString("nacionalidad").toLowerCase().equals(nacionalidad)) {
                    if (!found) {
                        System.out.println("\n--> Autores de la nacionalidad " + nacionalidad + ":\n");
                        found = true;
                    }
                    System.out.println("Nombre: " + document.getString("nombre"));
                    System.out.println("Fecha de nacimiento: " + document.getString("nacimiento"));
                    System.out.println("Géneros: " + document.getList("generos", String.class));
                    System.out.println("Obras destacadas: " + document.getList("obras_destacadas", String.class));
                    System.out.println("-------------------------");
                }
            }

            if (!found) {
                System.out.println("No hay autores de la nacionalidad " + nacionalidad + " en la base de datos.");
            }
        }
    }
}