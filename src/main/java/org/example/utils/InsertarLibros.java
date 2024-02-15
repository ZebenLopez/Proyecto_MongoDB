package org.example.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.example.services.Conexion;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * La clase InsertarLibros.
 *
 * @version 1.0  Curso 2ºA DAM Clase para la inserción de libros en la base de datos.
 * @autor: Zebenzuí López Conde
 */
@SuppressWarnings("all")
public class InsertarLibros {
    /**
     * Método para insertar libros predefinidos en la base de datos.<br>
     * Lee un archivo JSON con información de libros y los inserta en la base de datos.
     */
    public static void insertarLibrosPredefinidos() {
        try (MongoClient mongoClient = Conexion.conectar()) {
            MongoDatabase database = mongoClient.getDatabase("Libreria");
            MongoCollection<Document> collection = database.getCollection("Libros");

            String json = new String(Files.readAllBytes(Paths.get("src/main/resources/libros_ejemplo.json")));

            Gson gson = new Gson();
            Type type = new TypeToken<List<Map<String, Object>>>(){}.getType();
            List<Map<String, Object>> list = gson.fromJson(json, type);

            List<Document> documents = new ArrayList<>();
            for (Map<String, Object> map : list) {
                Document document = new Document(map);
                long count = collection.countDocuments(new Document("titulo", document.getString("titulo")));
                if (count == 0) {
                    documents.add(document);
                }
            }

            if (!documents.isEmpty()) {
                collection.insertMany(documents);
                System.out.println("Libros insertados correctamente.");
            } else {
                System.out.println("No se insertaron libros porque ya existen en la base de datos.");
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo JSON.");
        }
    }

    /**
     * Método para insertar libros de forma manual en la base de datos.<br>
     * Pide al usuario que introduzca la información del libro y luego lo inserta en la base de datos.
     */
    public static void insertarLibrosManual(){
        try (MongoClient mongoClient = Conexion.conectar()) {
            MongoDatabase database = mongoClient.getDatabase("Libreria");
            MongoCollection<Document> collection = database.getCollection("Libros");
            Scanner scanner = new Scanner(System.in);
            String titulo, isbn, autor, genero, idioma;
            double anio;
            System.out.println("Introduce el título del libro:");
            titulo = scanner.nextLine();
            System.out.println("Introduce el autor del libro:");
            autor = scanner.nextLine();
            System.out.println("Introduce el año de publicación del libro:");
            anio = scanner.nextDouble();
            System.out.println("Introduce el género del libro:");
            scanner.nextLine();
            genero = scanner.nextLine();
            System.out.println("Introduce el idioma del libro:");
            idioma = scanner.nextLine();
            Document document = new Document("titulo", titulo)
                    .append("autor", autor)
                    .append("anio_publicacion", anio)
                    .append("genero", genero)
                    .append("idioma", idioma);
            long count = collection.countDocuments(new Document("titulo", document.getString("titulo")));
            if (count == 0) {
                collection.insertOne(document);
                System.out.println("Libro insertado correctamente.");
            } else {
                System.out.println("No se insertó el libro porque ya existe en la base de datos.");
            }
        }
    }
}