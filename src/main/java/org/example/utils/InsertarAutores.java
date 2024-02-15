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
 * La clase InsertarAutores.
 *
 * @version 1.0  Curso 2ºA DAM Clase para la inserción de autores en la base de datos.
 * @autor: Zebenzuí López Conde
 */
@SuppressWarnings("all")
public class InsertarAutores {
    /**
     * Método para insertar autores predefinidos en la base de datos desde un archivo Json.
     */
    public static void insertarAutores() {
        try (MongoClient mongoClient = Conexion.conectar()) {
            MongoDatabase database = mongoClient.getDatabase("Libreria");
            MongoCollection<Document> collection = database.getCollection("Autores");

            String json = new String(Files.readAllBytes(Paths.get("src/main/resources/autores_ejemplo.json")));

            Gson gson = new Gson();
            Type type = new TypeToken<List<Map<String, Object>>>(){}.getType();
            List<Map<String, Object>> list = gson.fromJson(json, type);

            List<Document> documents = new ArrayList<>();
            for (Map<String, Object> map : list) {
                Document document = new Document(map);
                long count = collection.countDocuments(new Document("nombre", document.getString("nombre")));
                if (count == 0) {
                    documents.add(document);
                }
            }

            if (!documents.isEmpty()) {
                collection.insertMany(documents);
                System.out.println("Autores insertados correctamente.");
            } else {
                System.out.println("No se insertaron autores porque ya existen en la base de datos.");
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo JSON.");
        }
    }
    /**
     * Método para insertar autores de forma manual en la base de datos.
     */
    public static void insertarAutoresManual() {
        try (MongoClient mongoClient = Conexion.conectar()) {
            MongoDatabase database = mongoClient.getDatabase("Libreria");
            MongoCollection<Document> collection = database.getCollection("Autores");
            Scanner scanner = new Scanner(System.in);
            String nombre, nacimiento, nacionalidad;
            List<String> generos = new ArrayList<>();
            List<String> obras_destacadas = new ArrayList<>();

            System.out.println("Introduce el nombre del autor:");
            nombre = scanner.nextLine();
            System.out.println("Introduce la fecha de nacimiento del autor (YYYY-MM-DD):");
            nacimiento = scanner.nextLine();
            System.out.println("Introduce la nacionalidad del autor:");
            nacionalidad = scanner.nextLine();
            System.out.println("Introduce los géneros del autor (separados por comas):");
            String generosInput = scanner.nextLine();
            for (String genero : generosInput.split(",")) {
                generos.add(genero.trim());
            }
            System.out.println("Introduce las obras destacadas del autor (separadas por comas):");
            String obrasInput = scanner.nextLine();
            for (String obra : obrasInput.split(",")) {
                obras_destacadas.add(obra.trim());
            }

            Document document = new Document("nombre", nombre)
                    .append("nacimiento", nacimiento)
                    .append("nacionalidad", nacionalidad)
                    .append("generos", generos)
                    .append("obras_destacadas", obras_destacadas);

            long count = collection.countDocuments(new Document("nombre", document.getString("nombre")));
            if (count == 0) {
                collection.insertOne(document);
                System.out.println("Autor insertado correctamente.");
            } else {
                System.out.println("No se insertó el autor porque ya existe en la base de datos.");
            }
        }
    }
}