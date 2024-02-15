package org.example.utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class Conexion {
    public static void conectar() {
        try{
            MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
            System.out.println("Conectado a la base de datos.");
        } catch (Exception e) {
            System.out.println("No se pudo conectar a la base de datos.");
        }
    }
}
