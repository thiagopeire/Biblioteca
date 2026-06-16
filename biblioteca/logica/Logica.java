package biblioteca.logica;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import biblioteca.modelo.Libro;
import biblioteca.modelo.Prestamo;
import biblioteca.modelo.Socio;
import net.datastructures.LinkedPositionalList;
import net.datastructures.ProbeHashMap;

public class Logica {
    private ProbeHashMap<String, Libro> catalogo;
    private ProbeHashMap<String, Socio> socios;
    private ProbeHashMap<String,LinkedPositionalList<Prestamo>> prestamosActivos;
    // TODO: definir las estructuras adicionales que necesite
    // Pensar: ¿dónde guardar los préstamos activos? 
    // Pensar: ¿cómo modelar la lista de espera por libro?
    // Pensar: ¿dónde guardar el historial de préstamos por socio?

    public Logica(ProbeHashMap<String, Libro> catalogo,
                  ProbeHashMap<String, Socio> socios,
                  ProbeHashMap<String, LinkedPositionalList<Prestamo>> prestamosActivos) {
        this.catalogo = catalogo;
        this.socios   = socios;
        this.prestamosActivos = prestamosActivos;
    }

    // ── INCREMENTO 1 ──────────────────────────────────────────────

    /**
     * Registra el préstamo de un libro a un socio.
     * La fecha de préstamo es la fecha actual y el vencimiento se calcula
     * automáticamente (14 días).
     * Condiciones: el socio debe estar activo y debe haber ejemplares disponibles.
     * @return true si el préstamo se realizó, false en caso contrario
     */
     //TODO: insertar el nuevo prestamo en prestamosActivos
    public boolean prestar(String nroSocio, String isbn) {
        try (FileWriter filePrestamos = new FileWriter("prestamos.txt", true);
        PrintWriter escritor = new PrintWriter(filePrestamos)){
            Socio socio = socios.get(nroSocio);
            if (socio == null || !socio.isActive()){
                return false;
            }

            Libro libro = catalogo.get(isbn);
            if (libro==null || libro.getEjemplaresDisp() == 0){
                return false;
            }

            LocalDate fechaPrestamo = LocalDate.now();
            LocalDate vencimiento = fechaPrestamo.plusDays(14);

            escritor.println(nroSocio+";"+isbn+";"+formatearFecha(fechaPrestamo)+";"+formatearFecha(vencimiento)); 
            
        } catch (Exception e){
            System.out.println("LOGICA_ERROR: "+e.getLocalizedMessage() + "\nDetalle: "+e.getClass().toGenericString());//debug
            return false;
        }
       
        return true;
    }

    private String formatearFecha(LocalDate fecha){
        return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    /**
     * Registra la devolución de un libro.
     * Actualiza el estado del préstamo y la disponibilidad del libro.
     * @return true si la devolución se realizó, false en caso contrario
     */
    public boolean devolver(String nroSocio, String isbn) {
        // TODO: implementar
        return false;
    }

    /**
     * Busca un libro por su ISBN.
     * @return el Libro encontrado, o null si no existe
     */
    public Libro buscarPorIsbn(String isbn) {
        // TODO: implementar
        return null;
    }

    /**
     * Busca libros cuyo título contenga la cadena indicada (sin distinguir mayúsculas).
     */
    public LinkedPositionalList<Libro> buscarPorTitulo(String titulo) {
        // TODO: implementar
        return null;
    }

    /**
     * Busca libros de un autor dado (sin distinguir mayúsculas).
     */
    public LinkedPositionalList<Libro> buscarPorAutor(String autor) {
        // TODO: implementar
        return null;
    }

    /**
     * Retorna todos los libros con al menos un ejemplar disponible.
     */
    public LinkedPositionalList<Libro> listarDisponibles() {
        // TODO: implementar
        return null;
    }

    /**
     * Retorna los préstamos activos de un socio.
     */
    public LinkedPositionalList<Prestamo> prestamosActivosDeSocio(String nroSocio) {
        // TODO: implementar
        return null;
    }

    // ── INCREMENTO 2 ──────────────────────────────────────────────

    /**
     * Agrega un socio a la cola de espera de un libro.
     * Se invoca cuando no hay ejemplares disponibles al momento del pedido.
     */
    public void agregarEspera(String nroSocio, String isbn) {
        // TODO: implementar
    }

    /**
     * Al devolver un libro, si hay socios en espera, asigna el ejemplar
     * automáticamente al primero en la cola y lo notifica.
     */
    public void asignarSiguienteEnEspera(String isbn) {
        // TODO: implementar
    }

    /**
     * Retorna el historial completo de préstamos de un socio
     * (activos e históricos), en orden cronológico.
     */
    public LinkedPositionalList<Prestamo> historialDeSocio(String nroSocio) {
        // TODO: implementar
        return null;
    }

    /**
     * Retorna los N libros más solicitados (préstamos activos + históricos).
     * @param n cantidad de libros a retornar
     */
    public LinkedPositionalList<Libro> librosMasSolicitados(int n) {
        // TODO: implementar
        return null;
    }

    /**
     * Retorna todos los préstamos cuya fecha de vencimiento expiró
     * y que aún no fueron devueltos.
     * @param hoy fecha actual
     */
    public LinkedPositionalList<Prestamo> prestamosVencidos(LocalDate hoy) {
        // TODO: implementar
        return null;
    }
}