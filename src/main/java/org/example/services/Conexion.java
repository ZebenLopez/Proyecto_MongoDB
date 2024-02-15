package org.example.services;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
/**
 * La clase Conexion.
 *
 * @version 1.0  Curso 2ºA DAM Clase para la conexión a la base de datos.
 * @autor: Zebenzuí López Conde
 */
@SuppressWarnings("all")
public class Conexion {
    /**
     * Método para conectar a la base de datos MongoDB.
     *
     * @return MongoClient si la conexión es exitosa, null en caso contrario.
     */
    public static MongoClient conectar() {
        MongoClient mongoClient = null;
        try{
            mongoClient = MongoClients.create("mongodb://localhost:27017");
            System.out.println("Conectado a la base de datos.");
        } catch (Exception e) {
            System.out.println("No se pudo conectar a la base de datos.");
        }
        return mongoClient;
    }
}