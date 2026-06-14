package biblioteca.interfaz;

import java.io.InputStream;

import biblioteca.aplicacion.Constante;
import biblioteca.modelo.Libro;
import biblioteca.modelo.Prestamo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class Interfaz {
    private static Scanner SC = new Scanner(System.in);
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Muestra el menú principal y retorna la opción elegida por el usuario.
     * @return De ingresarse una opcion valida la devuelve, en caso contrario -1
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
        
        Integer opcion = SC.nextInt();
        // System.out.println("\nInput: "+opcion.toString()); //debug
        mostrarError("El valor ingresado no se encuentra entre las opciones disponibles.");
        if (opcion < 0 || opcion >= Constante.TOTAL_OPCIONES) {
            return -1;
        }
        return opcion;
    }

    /**
     * Usa in como input para el scanner.
     * @param in entrada de datos
     */
    public static void setScanner(InputStream in){
        SC = new Scanner(in);
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
        System.out.print("Ingrese " + etiqueta +"(dd/mm/yyyy): ");
        try{
            String fechastr = SC.next();
            return LocalDate.parse(fechastr, FMT);
        }catch (Exception e){
            mostrarError(e.getLocalizedMessage());
        }
        return null;
    }
    // ── Métodos de presentación de resultados ──
    public static void mostrarLibro(Libro libro) {
        // TODO: implementar getters
        System.out.println("---------------------------------------------------");
        System.out.println(libro.toString());
        System.out.println("---------------------------------------------------");

    }

    public static void mostrarListaLibros(Iterable<Libro> libros) {
        for (Libro libro : libros){
            mostrarLibro(libro);
        }
    }

     /**
     * Muestra la lista de préstamos por pantalla.
     * Itera sobre los préstamos recibidos y muestra cada uno
     * usando el método {@link Prestamo#toString()}.
     *
     * @param prestamos colección iterable de préstamos a mostrar
     */
    public static void mostrarListaPrestamos(Iterable<Prestamo> prestamos) {
        // TODO: implementar
        for (Prestamo prestamo : prestamos) {
            System.out.println("---------------------------------------------------");
            System.out.println(prestamo.toString());
            System.out.println("---------------------------------------------------");
        }
    }

    public static void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public static void mostrarError(String mensaje) {
        System.err.println("ERROR: " + mensaje);
    }
}
