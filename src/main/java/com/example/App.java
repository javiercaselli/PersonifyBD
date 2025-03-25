package com.example;

import com.example.entidades.Individuo;
import com.example.entidades.IndividuoService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * PersonifyBD
 *
 */
public class App {
    // Usuario BD
    static String usuario = "javier";
    // Contraseña BD
    static String password = "javier123";
    // URL de conexión
    static String url = "jdbc:mysql://localhost:3306/personify";
    // Scanner
    static Scanner sc = new Scanner(System.in);
    // Servicio de individuos
    static IndividuoService individuoService = null;


    public static void main( String[] args ) {
        //Variables
        Connection con = null;
        int opcion = 0;

        try {
            // Establece conexión con BD
            con = DriverManager.getConnection(url, usuario, password);

            // Inicializa servicio para tratamiento de individuos
            individuoService = new IndividuoService(con);

            // Menú
            do {
                mostrarMenu();
                System.out.print("Introduzca una opción: ");
                opcion = Integer.parseInt(sc.nextLine());
                switch (opcion) {
                    case 1 -> listarIndividuos();
                }

            } while (opcion != 7);

        } catch (SQLException e) {
            System.out.println("Error de conexión " + e.getMessage());
        } finally {
            sc.close();
        }




    }

    private static void listarIndividuos() throws SQLException {
        ArrayList<Individuo> listaIndividuos = individuoService.requestAll("id", "asc");
        for (Individuo individuo : listaIndividuos) {
            System.out.println(individuo);
        }
    }

    static void mostrarMenu() {
        System.out.println("\n********* PERSONIFY ********\n");
        System.out.println("1. Listar individuos");
        System.out.println("2. Añadir individuo");
        System.out.println("3. Modificar individuo");
        System.out.println("4. Eliminar individuo");
        System.out.println("5. Listar descencientes de un individuo");
        System.out.println("6. Listar ascendientes de un individuo");
        System.out.println("7. Salir");
    }


}
