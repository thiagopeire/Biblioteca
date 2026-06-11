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
                    break;

                case Constante.OPCION_BUSCAR_TITULO:
                    // TODO: pedir título y mostrar resultados de logica.buscarPorTitulo(...)
                    break;

                case Constante.OPCION_BUSCAR_AUTOR:
                    // TODO: pedir autor y mostrar resultados de logica.buscarPorAutor(...)
                    break;

                case Constante.OPCION_DISPONIBLES:
                    // TODO: mostrar resultado de logica.listarDisponibles()
                    break;

                case Constante.OPCION_PRESTAMOS_SOCIO:
                    // TODO: pedir nroSocio y mostrar logica.prestamosActivosDeSocio(...)
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
