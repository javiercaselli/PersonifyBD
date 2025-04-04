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
 */
public class App {
    // Usuario BD
    static String usuario = "javier";
    static String usuarioSqlServer = "personifyuser";
    // Contraseña BD
    static String password = "javier123";
    static String passwordSqlServer = "12345";
    // URL de conexión
    static String url = "jdbc:mysql://localhost:3306/personify";
    static String urlSqlServer = "jdbc:sqlserver://localhost\\SQLEXPRESS03:1434;encrypt=false";
    // Scanner
    static Scanner sc = new Scanner(System.in);
    // Servicio de individuos
    static IndividuoService individuoService = null;


    public static void main(String[] args) {
        //Variables
        Connection con = null;
        int opcion = 0;

        try {
            // Establece conexión con BD
            // con = DriverManager.getConnection(url, usuario, password);
            con = DriverManager.getConnection(urlSqlServer, usuarioSqlServer, passwordSqlServer);

            // Inicializa servicio para tratamiento de individuos
            individuoService = new IndividuoService(con);

            // Menú
            do {
                mostrarMenu();
                System.out.print("Introduzca una opción: ");
                opcion = Integer.parseInt(sc.nextLine());
                switch (opcion) {
                    case 1 -> listarIndividuos();
                    case 2 -> addIndividuo();
                    case 3 -> modificarIndividuo();
                    case 4 -> eliminarIndividuo();
                    case 5 -> listarDescendientes();
                    case 6 -> listarAscendientes();
                    case 7 -> System.out.println("ADIOS");
                    default -> System.out.println("Opción incorrecta");
                }

            } while (opcion != 7);

        } catch (SQLException e) {
            System.out.println("Error de conexión " + e.getMessage());
        } finally {
            sc.close();
        }


    }

    private static void listarAscendientes() {
    }

    private static void listarDescendientes() throws SQLException {
        // Obtiene al individuo de base de datos (si existe)
        Individuo individuoPadre = getIndividuo();

        if (individuoPadre != null) {
            // Busco los individuos que tiene como progenitor al individuo raíz
            ArrayList<Individuo> individuosHijos = individuoService.requestByProgenitor(individuoPadre);
            System.out.println("\nListado de descencientes de " + individuoPadre);
            for (Individuo individuo : individuosHijos) {
                System.out.println(individuo);
            }
        } else {
            System.out.println("El individuo no existe");
        }
    }

    private static void eliminarIndividuo() {
    }

    private static void modificarIndividuo() {
    }

    private static void addIndividuo() throws SQLException {
        // Individuo a insertar en BD
        Individuo ind = new Individuo();

        // Recibe datos del nuevo individuo por teclado (entrada estándar)
        System.out.print("Introduzca un nombre: ");
        ind.setNombre(sc.nextLine());
        System.out.print("Introduzca el primer apellido: ");
        ind.setApellido1(sc.nextLine());
        System.out.print("Introduzca el segundo apellido: ");
        ind.setApellido2(sc.nextLine());
        System.out.print("Introduzca el progenitor 1: ");
        String prog1 = sc.nextLine();
        ind.setProgenitor1((prog1.isEmpty()) ? null : Long.valueOf(prog1));
        System.out.print("Introduzca el progenitor 2: ");
        String prog2 = sc.nextLine();
        ind.setProgenitor2((prog2.isEmpty()) ? null : Long.valueOf(prog2));

        // Insertamos al individuo a través del servicio correspondiente
        long filas = individuoService.create(ind);

        if (filas > 0) System.out.println("El individuo se ha registrado correctamente");
        else System.out.println("El individuo no se ha registrado");
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

    private static Individuo getIndividuo() throws SQLException {
        System.out.print("Introduzca el ID del individuo: ");
        long id = Long.parseLong(sc.nextLine());

        // Comprueba si el individuo existe
        return individuoService.requestById(id);
    }


}
