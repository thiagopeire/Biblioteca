package biblioteca.aplicacion;

import java.io.IOException;

import biblioteca.datos.CargarParametros;
import biblioteca.datos.Dato;
import biblioteca.interfaz.Interfaz;
import biblioteca.logica.Logica;
import biblioteca.modelo.Libro;
import biblioteca.modelo.Prestamo;
import biblioteca.modelo.Socio;
import net.datastructures.LinkedPositionalList;
import net.datastructures.ProbeHashMap;

public class Aplicacion {

    public static void main(String[] args) {

        // 1. Cargar parámetros de configuración
        try {
            CargarParametros.parametros();
        } catch (IOException e) {
            System.err.println("Error al cargar config.properties");
            System.exit(-1);
        }

        // 2. Cargar datos desde archivos
        ProbeHashMap<String, Libro>   catalogo  = null;
        ProbeHashMap<String, Socio>   socios    = null;
        ProbeHashMap<String, LinkedPositionalList<Prestamo>> prestamos = null;

        try {
            catalogo  = Dato.cargarLibros(CargarParametros.getArchivoLibros());
            socios    = Dato.cargarSocios(CargarParametros.getArchivoSocios());
            prestamos = Dato.cargarPrestamos(CargarParametros.getArchivoPrestamos(), 
                                                socios, catalogo);
        } catch (Exception e) {
            System.err.println("Error al cargar archivos de datos: " + e.getMessage());
            System.exit(-1);
        }

        // 3. Inicializar capa lógica
        Logica logica = new Logica(catalogo, socios, prestamos);

        // 4. Ciclo principal de la aplicación
        int opcion;
        do {
            opcion = Interfaz.menu();

            switch (opcion) {
                case Constante.OPCION_PRESTAR:
                    Interfaz.mostrarMensaje("Prestar");
                    String socioPrestar = Interfaz.pedirNroSocio();
                    String isbnPrestar = Interfaz.pedirIsbn();

                    if (!logica.prestar(socioPrestar, isbnPrestar)){
                        Interfaz.mostrarError("No se pudo concretar la operacion.");
                        break;
                    }
                    Interfaz.mostrarMensaje("Exito.");
                    break;

                case Constante.OPCION_DEVOLVER:
                    Interfaz.mostrarMensaje("Devolver");
                    String socioDevolver = Interfaz.pedirNroSocio();
                    String isbnDevolver = Interfaz.pedirIsbn();

                    if (!logica.devolver(socioDevolver, isbnDevolver)){
                        Interfaz.mostrarError("No se pudo concretar la operacion.");
                        break;
                    }
                    Interfaz.mostrarMensaje("Exito.");
                    break;

                case Constante.OPCION_BUSCAR_ISBN:
                    Interfaz.mostrarMensaje("Busqueda");
                	String isbnBuscar = Interfaz.pedirIsbn();
                    Libro libroEncontrado = logica.buscarPorIsbn(isbnBuscar);
                    if (libroEncontrado == null) {
                        Interfaz.mostrarError("No se encontro un libro con ese ISBN.");
                    } else {
                        Interfaz.mostrarLibro(libroEncontrado);
                    }
                    break;

                case Constante.OPCION_BUSCAR_TITULO:
                    String titulo = Interfaz.pedirTitulo();
                    LinkedPositionalList<Libro> porTitulo = logica.buscarPorTitulo(titulo);
                    if (porTitulo.isEmpty()) {
                        Interfaz.mostrarError("No se encontraron libros con ese titulo.");
                    } else {
                        Interfaz.mostrarListaLibros(porTitulo);
                    }
                    break;

                case Constante.OPCION_BUSCAR_AUTOR:
                    String autor = Interfaz.pedirAutor();
                    LinkedPositionalList<Libro> porAutor = logica.buscarPorAutor(autor);
                    if (porAutor.isEmpty()) {
                        Interfaz.mostrarError("No se encontraron libros de ese autor.");
                    } else {
                        Interfaz.mostrarListaLibros(porAutor);
                    }
                    break;

                case Constante.OPCION_DISPONIBLES:
                    LinkedPositionalList<Libro> disponibles = logica.listarDisponibles();
                    if (disponibles.isEmpty()) {
                        Interfaz.mostrarMensaje("No hay libros disponibles en este momento.");
                    } else {
                        Interfaz.mostrarListaLibros(disponibles);
                    }
                    break;

                case Constante.OPCION_PRESTAMOS_SOCIO:
                    String socioPrestamos = Interfaz.pedirNroSocio();
                    LinkedPositionalList<Prestamo> activosSocio = logica.prestamosActivosDeSocio(socioPrestamos);
                    if (activosSocio.isEmpty()) {
                        Interfaz.mostrarMensaje("El socio no tiene prestamos activos.");
                    } else {
                        Interfaz.mostrarListaPrestamos(activosSocio);
                    }
                    break;

                case Constante.OPCION_HISTORIAL:
                    // NUEVO: Pide el número de socio al usuario
                    String socioHistorial = Interfaz.pedirNroSocio();

                    // NUEVO: Obtiene el historial completo de préstamos del socio
                    LinkedPositionalList<Prestamo> historial = logica.historialDeSocio(socioHistorial);

                    // NUEVO: Si el historial está vacío, informa al usuario
                    if (historial.isEmpty()) {
                    Interfaz.mostrarMensaje("El socio no tiene historial de préstamos.");
                    } else {
                    // NUEVO: Muestra cada préstamo del historial por pantalla
                    Interfaz.mostrarListaPrestamos(historial);
                    }
                    break;

                case Constante.OPCION_RANKING:
                    // NUEVO: Pide al usuario cuántos libros quiere ver en el ranking
                    int n = Interfaz.pedirN();

                    // NUEVO: Obtiene la lista de libros más solicitados
                    LinkedPositionalList<Libro> ranking = logica.librosMasSolicitados(n);

                    // NUEVO: Si no hay datos de préstamos, informa al usuario
                    if (ranking.isEmpty()) {
                    Interfaz.mostrarMensaje("No hay datos de préstamos para generar el ranking.");
                    } else {
                    // NUEVO: Muestra los libros del ranking por pantalla
                    Interfaz.mostrarListaLibros(ranking);
                    }
                    break;

                case Constante.OPCION_VENCIDOS:
                    // NUEVO: Pide la fecha al usuario para comparar contra los vencimientos
                    java.time.LocalDate fechaVencidos = Interfaz.pedirFecha("fecha de referencia ");

                    // NUEVO: Si la fecha ingresada no es válida, muestra error y no continúa
                    if (fechaVencidos == null) {
                    Interfaz.mostrarError("Fecha inválida, operación cancelada.");
                    break;
                    }

                    // NUEVO: Obtiene todos los préstamos vencidos hasta la fecha indicada
                    LinkedPositionalList<Prestamo> vencidos = logica.prestamosVencidos(fechaVencidos);

                    // NUEVO: Si no hay préstamos vencidos, informa al usuario
                    if (vencidos.isEmpty()) {
                    Interfaz.mostrarMensaje("No hay préstamos vencidos a esa fecha.");
                    } else {
                    // NUEVO: Muestra cada préstamo vencido por pantalla
                    Interfaz.mostrarListaPrestamos(vencidos);
                    }
                    break;

                case Constante.OPCION_SALIR:
                    Interfaz.mostrarMensaje("Hasta luego.");
                    break;

                default:
                    Interfaz.mostrarError("Opción no válida.");
            }

        } while (opcion != Constante.OPCION_SALIR);
    }
}
