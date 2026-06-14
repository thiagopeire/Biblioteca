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
                    // TODO: pedir datos al usuario y llamar a logica.prestar(...)
                    Interfaz.mostrarMensaje("Prestar");
                    String socioPrestar = Interfaz.pedirNroSocio();
                    String isbnPrestar = Interfaz.pedirIsbn();

                    if (!logica.prestar(socioPrestar, isbnPrestar)){
                        Interfaz.mostrarError("No se pudo concretar el prestamo.");
                    }
                    Interfaz.mostrarMensaje("prestamo cargado correctamente.");
                    break;

                case Constante.OPCION_DEVOLVER:
                    // TODO: pedir datos al usuario y llamar a logica.devolver(...)
                    Interfaz.mostrarMensaje("Devolver");
                    String socioDevolver = Interfaz.pedirNroSocio();
                    String isbnDevolver = Interfaz.pedirIsbn();

                    logica.devolver(socioDevolver, isbnDevolver);
                    break;

                case Constante.OPCION_BUSCAR_ISBN:
                    // TODO: pedir ISBN y mostrar resultado de logica.buscarPorIsbn(...)
                    // Pide el ISBN al usuario y busca el libro en el catálogo.
                    // Si no existe muestra un error, si existe lo muestra por pantalla.
                	String isbnBuscar = Interfaz.pedirIsbn();
                    Libro libroEncontrado = logica.buscarPorIsbn(isbnBuscar);
                    if (libroEncontrado == null) {
                        Interfaz.mostrarError("No se encontro un libro con ese ISBN.");
                    } else {
                        Interfaz.mostrarLibro(libroEncontrado);
                    }
                    break;

                case Constante.OPCION_BUSCAR_TITULO:
                    // TODO: pedir título y mostrar resultados de logica.buscarPorTitulo(...)
                    // Pide el título al usuario y busca todos los libros que lo contengan.
                    // La búsqueda no distingue mayúsculas de minúsculas.
                    String titulo = Interfaz.pedirTitulo();
                    LinkedPositionalList<Libro> porTitulo = logica.buscarPorTitulo(titulo);
                    if (porTitulo.isEmpty()) {
                        Interfaz.mostrarError("No se encontraron libros con ese titulo.");
                    } else {
                        Interfaz.mostrarListaLibros(porTitulo);
                    }
                    break;

                case Constante.OPCION_BUSCAR_AUTOR:
                    // TODO: pedir autor y mostrar resultados de logica.buscarPorAutor(...)
                    // Pide el autor al usuario y busca todos los libros de ese autor.
                    // La búsqueda no distingue mayúsculas de minúsculas.
                    String autor = Interfaz.pedirAutor();
                    LinkedPositionalList<Libro> porAutor = logica.buscarPorAutor(autor);
                    if (porAutor.isEmpty()) {
                        Interfaz.mostrarError("No se encontraron libros de ese autor.");
                    } else {
                        Interfaz.mostrarListaLibros(porAutor);
                    }
                    break;

                case Constante.OPCION_DISPONIBLES:
                    // TODO: mostrar resultado de logica.listarDisponibles()
                    // Obtiene todos los libros con al menos un ejemplar disponible y los muestra.
                    LinkedPositionalList<Libro> disponibles = logica.listarDisponibles();
                    if (disponibles.isEmpty()) {
                        Interfaz.mostrarMensaje("No hay libros disponibles en este momento.");
                    } else {
                        Interfaz.mostrarListaLibros(disponibles);
                    }
                    break;

                case Constante.OPCION_PRESTAMOS_SOCIO:
                    // TODO: pedir nroSocio y mostrar logica.prestamosActivosDeSocio(...)
                     // Pide el número de socio y muestra todos sus préstamos activos.
                    String socioPrestamos = Interfaz.pedirNroSocio();
                    LinkedPositionalList<Prestamo> activosSocio = logica.prestamosActivosDeSocio(socioPrestamos);
                    if (activosSocio.isEmpty()) {
                        Interfaz.mostrarMensaje("El socio no tiene prestamos activos.");
                    } else {
                        Interfaz.mostrarListaPrestamos(activosSocio);
                    }
                    break;

                case Constante.OPCION_HISTORIAL:
                    // TODO: pedir nroSocio y mostrar logica.historialDeSocio(...)
                    break;

                case Constante.OPCION_RANKING:
                    // TODO: pedir N y mostrar logica.librosMasSolicitados(N)
                    break;

                case Constante.OPCION_VENCIDOS:
                    // TODO: pedir fecha con Interfaz.pedirFecha(...) y mostrar
                    //       logica.prestamosVencidos(LocalDate)
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
