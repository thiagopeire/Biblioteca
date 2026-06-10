package biblioteca.interfaz;

import biblioteca.aplicacion.Constante;
import biblioteca.modelo.Libro;
import biblioteca.modelo.Prestamo;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Interfaz {
    private static final Scanner SC = new Scanner(System.in);
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Muestra el menú principal y retorna la opción elegida por el usuario.
     */
    public static int menu() {
        System.out.println("\n===== SISTEMA DE GESTIÓN DE BIBLIOTECA =====");
        System.out.println(Constante.OPCION_PRESTAR + ". Registrar préstamo");
        System.out.println(Constante.OPCION_DEVOLVER + ". Registrar devolución");
        System.out.println(Constante.OPCION_BUSCAR_ISBN + ". Buscar libro por ISBN");
        System.out.println(Constante.OPCION_BUSCAR_TITULO + ". Buscar libro por título");
        System.out.println(Constante.OPCION_BUSCAR_AUTOR + ". Buscar libro por autor");
        System.out.println(Constante.OPCION_DISPONIBLES + ". Listar libros disponibles");
        System.out.println(Constante.OPCION_PRESTAMOS_SOCIO + ". Ver préstamos activos de un socio");
        System.out.println("---- Incremento 2 ----");
        System.out.println(Constante.OPCION_HISTORIAL + ". Ver historial de un socio");
        System.out.println(Constante.OPCION_RANKING + ". Libros más solicitados");
        System.out.println(Constante.OPCION_VENCIDOS + ". Préstamos vencidos");
        System.out.println(Constante.OPCION_SALIR + ". Salir");
        System.out.print("Ingrese una opción: ");

        int opcion = SC.nextInt();
        if (opcion < 0 | opcion >= Constante.TOTAL_OPCIONES) {
            mostrarError("El valor ingresado no se encuentra entre las opciones disponibles.");
        }
        return opcion;
    }

    public static String pedirIsbn() {
        System.out.print("Ingrese ISBN: ");
        return SC.next();
    }

    public static String pedirNroSocio() {
        System.out.print("Ingrese número de socio: ");
        return  SC.next();
    }

    public static String pedirTitulo() {
        System.out.print("Ingrese título (o parte del título): ");
        return SC.next();
    }

    public static String pedirAutor() {
        System.out.print("Ingrese nombre del autor: ");
        return SC.next();
    }

    public static int pedirN() {
        System.out.print("Ingrese cantidad de libros a mostrar: ");
        return SC.nextInt();
    }

    /**
     * Solicita una fecha al usuario en formato dd/MM/yyyy y la retorna como
     * LocalDate. Debe validar el formato antes de retornar.
     */
    public static LocalDate pedirFecha(String etiqueta) {
        System.out.print("Ingrese " + etiqueta + " (dd/MM/yyyy): ");
        // TODO: implementar y validar formato usando DateTimeFormatter FMT
        return null;
    }

    // ── Métodos de presentación de resultados ──
    public static void mostrarLibro(Libro libro) {
        // TODO: implementar
    }

    public static void mostrarListaLibros(Iterable<Libro> libros) {
        // TODO: implementar
    }

    public static void mostrarListaPrestamos(Iterable<Prestamo> prestamos) {
        // TODO: implementar
    }

    public static void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public static void mostrarError(String mensaje) {
        System.err.println("ERROR: " + mensaje);
    }
}
