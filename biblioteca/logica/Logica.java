package biblioteca.logica;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;

import biblioteca.aplicacion.Constante;
import biblioteca.modelo.Libro;
import biblioteca.modelo.Prestamo;
import biblioteca.modelo.Socio;
import net.datastructures.Entry;
import net.datastructures.LinkedPositionalList;
import net.datastructures.ProbeHashMap;

public class Logica {
    private ProbeHashMap<String, Libro> catalogo;
    private ProbeHashMap<String, Socio> socios;
    private ProbeHashMap<String,LinkedPositionalList<Prestamo>> prestamosActivos;

    private ProbeHashMap<String, LinkedPositionalList<Socio>> listasEspera; //por isbn

    private ProbeHashMap<String, LinkedPositionalList<Prestamo>> historialPrestamos; //por nroSocio

    public Logica(ProbeHashMap<String, Libro> catalogo,
                  ProbeHashMap<String, Socio> socios,
                  ProbeHashMap<String, LinkedPositionalList<Prestamo>> prestamosActivos) {
        this.catalogo = catalogo;
        this.socios   = socios;
        this.prestamosActivos = prestamosActivos;
        this.listasEspera = new ProbeHashMap<>();
        this.historialPrestamos = new ProbeHashMap<>();

        for (String nroSocio : prestamosActivos.keySet()) {
            LinkedPositionalList<Prestamo> lista = prestamosActivos.get(nroSocio);
            LinkedPositionalList<Prestamo> historial = new LinkedPositionalList<>();
            for (Prestamo p : lista) {
                historial.addLast(p);
            }
            this.historialPrestamos.put(nroSocio, historial);
        }
    }

    // ── INCREMENTO 1 ──────────────────────────────────────────────

    /**
     * Registra el préstamo de un libro a un socio.
     * La fecha de préstamo es la fecha actual y el vencimiento se calcula
     * automáticamente (14 días).
     * Condiciones: el socio debe estar activo y debe haber ejemplares disponibles.
     * @return true si el préstamo se realizó, false en caso contrario
     */
    public boolean prestar(String nroSocio, String isbn) {
        try (FileWriter filePrestamos = new FileWriter("prestamos.txt", true);
        PrintWriter escritor = new PrintWriter(filePrestamos)){
            Socio socio = socios.get(nroSocio);
            if (socio == null || !socio.estaActivo()){
                return false;
            }

            Libro libro = catalogo.get(isbn);
            if (libro==null || libro.getEjemplaresDisp() == 0){
                return false;
            }

            LocalDate fechaPrestamo = LocalDate.now();
            LocalDate vencimiento = fechaPrestamo.plusDays(14);

            // NUEVO: Se crea el objeto Prestamo para poder
            // almacenarlo en memoria y utilizarlo en los reportes.
            Prestamo prestamo = new Prestamo(
                    socio,
                    libro,
                    fechaPrestamo,
                    vencimiento
                );

            // NUEVO: Obtiene la lista de préstamos activos del socio.
            LinkedPositionalList<Prestamo> activos = prestamosActivos.get(nroSocio);

            // NUEVO: Si el socio todavía no tiene préstamos activos,
            // se crea una nueva lista.
            if (activos == null) {
                activos = new LinkedPositionalList<>();
                prestamosActivos.put(nroSocio, activos);
            }

            // NUEVO: Agrega el préstamo a los préstamos activos.
            activos.addLast(prestamo);

            // NUEVO: Obtiene el historial completo del socio.
            LinkedPositionalList<Prestamo> historial = historialPrestamos.get(nroSocio);

            // NUEVO: Si el socio no posee historial todavía,
            // se crea una nueva lista.
            if (historial == null) {
                historial = new LinkedPositionalList<>();
                historialPrestamos.put(nroSocio, historial);
            }

            // NUEVO: Guarda el préstamo en el historial permanente.
            historial.addLast(prestamo);

            // NUEVO: Se descuenta un ejemplar disponible porque
            // el libro acaba de ser prestado.
            libro.setEjemplaresDisponibles(libro.getEjemplaresDisp() - 1);
            
            escritor.println("\n"+nroSocio+";"+isbn+";"+fechaPrestamo.format(Constante.FMT)+";"+vencimiento.format(Constante.FMT));            
        } catch (Exception e){
            System.out.println("LOGICA_ERROR: "+e.getLocalizedMessage() + "\nDetalle: "+e.getClass().toGenericString());//debug
            return false;
        }
       
        return true;
    }


    /**
     * Registra la devolución de un libro.
     * Actualiza el estado del préstamo y la disponibilidad del libro.
     * @return true si la devolución se realizó, false en caso contrario
     */
    public boolean devolver(String nroSocio, String isbn) {
        LinkedPositionalList<Prestamo> lista = prestamosActivos.get(nroSocio);

        if (lista == null) { return false; }

        for (Prestamo p : lista) {
            if (p.estaActivo() && p.getLibro().getIsbn().equals(isbn)) {

                p.setActivo(false);

                Libro libro = p.getLibro();

                libro.setEjemplaresDisponibles(libro.getEjemplaresDisp() + 1);
                
                asignarSiguienteEnEspera(isbn);

                return true;
            }
        }
        return false;
    }

    /**
     * Busca un libro por su ISBN.
     * @return el Libro encontrado, o null si no existe
     */
    public Libro buscarPorIsbn(String isbn) {
        // TODO: implementar
        Libro libro = catalogo.get(isbn);

        if (libro == null) { return null; }

        return libro;
    }

    /**
     * Busca libros cuyo título contenga la cadena indicada (sin distinguir mayúsculas).
     */
    public LinkedPositionalList<Libro> buscarPorTitulo(String titulo) {
        LinkedPositionalList<Libro> resultado = new LinkedPositionalList<>();

        String buscado = titulo.toLowerCase();

        for (Libro libro : catalogo.values()) {
            if (libro.getTitulo().toLowerCase().contains(buscado)) {
                resultado.addLast(libro);
            }
        }

        return resultado;
    }

    /**
     * Busca libros de un autor dado (sin distinguir mayúsculas).
     */
    public LinkedPositionalList<Libro> buscarPorAutor(String autor) {
        LinkedPositionalList<Libro> resultado = new LinkedPositionalList<>();

        String buscado = autor.toLowerCase();

        for (Libro libro : catalogo.values()) {
            if (libro.getAutor()
                    .toLowerCase()
                    .contains(buscado)) {

                resultado.addLast(libro);
            }
        }

        return resultado;
    }

    /**
     * Retorna todos los libros con al menos un ejemplar disponible.
     */
    public LinkedPositionalList<Libro> listarDisponibles() {
        LinkedPositionalList<Libro> resultado = new LinkedPositionalList<>();

        for (Libro libro : catalogo.values()) {

            if (libro.getEjemplaresDisp() > 0) {
                resultado.addLast(libro);
            }
        }

        return resultado;
    }

    /**
     * Retorna los préstamos activos de un socio.
     * @param nroSocio 
     * @return Lista de prestamos del socio.
     */
    public LinkedPositionalList<Prestamo> prestamosActivosDeSocio(String nroSocio) {
        LinkedPositionalList<Prestamo> resultado = new LinkedPositionalList<>();

        LinkedPositionalList<Prestamo> lista = prestamosActivos.get(nroSocio);

        if (lista == null) { return resultado; }

        for (Prestamo p : lista) {
            if (p.estaActivo()) {
                resultado.addLast(p);
            }
        }

        return resultado;
    }

    // ── INCREMENTO 2 ──────────────────────────────────────────────

    /**
     * Agrega un socio a la cola de espera de un libro.
     * Se invoca cuando no hay ejemplares disponibles al momento del pedido.
     */
    public void agregarEspera(String nroSocio, String isbn) {
        Socio socio = socios.get(nroSocio);

        if (socio == null) { return; }

        // NUEVO: Obtiene la lista de espera asociada al libro
        LinkedPositionalList<Socio> espera = listasEspera.get(isbn);

        // NUEVO: Si no existe una lista de espera para ese ISBN se crea
        if (espera == null) {
            espera = new LinkedPositionalList<>();
            listasEspera.put(isbn, espera);
        }

        // NUEVO: Se agrega al final para respetar el orden de llegada
        espera.addLast(socio);
    }

    /**
     * Al devolver un libro, si hay socios en espera, asigna el ejemplar
     * automáticamente al primero en la cola y lo notifica.
     */
    private void asignarSiguienteEnEspera(String isbn) {
        // NUEVO: Obtiene la cola de espera del libro
        LinkedPositionalList<Socio> espera = listasEspera.get(isbn);

        if (espera == null || espera.isEmpty()) { return; }

        // NUEVO: Recupera al primer socio de la cola
        Socio socio = espera.first().getElement();

        // NUEVO: Lo elimina de la cola porque ya será atendido
        espera.remove(espera.first());

        // NUEVO: Intenta generar automáticamente el préstamo
        boolean exito = prestar(socio.getNroSocio(), isbn);

        if (!exito){
            System.out.println( "No se ha podido concretar la asignacion del libro");
        }
        
        // NUEVO: Mensaje informativo
        System.out.println( "Libro asignado automáticamente al socio " + socio.getNroSocio());

    }

    /**
     * Retorna el historial completo de préstamos de un socio
     * (activos e históricos), en orden cronológico.
     */
    public LinkedPositionalList<Prestamo> historialDeSocio(String nroSocio) {
        // NUEVO: Obtiene el historial almacenado del socio
        LinkedPositionalList<Prestamo> historial = historialPrestamos.get(nroSocio);
        // NUEVO: Si no existe historial devuelve una lista vacía
        if (historial == null) {
            return new LinkedPositionalList<>();
        }

        return historial;
    }

    /**
     * Retorna los N libros más solicitados (préstamos activos + históricos).
     * @param n cantidad de libros a retornar
     */
    public LinkedPositionalList<Libro> librosMasSolicitados(int n) {
        LinkedPositionalList<Libro> resultado = new LinkedPositionalList<>();

        ProbeHashMap<Libro,Integer> solicitudesPorLibro = new ProbeHashMap<>(); 

        for (LinkedPositionalList<Prestamo> TotalprestamosDeSocio :historialPrestamos.values()) {
            for (Prestamo prestamo : TotalprestamosDeSocio) {
                Libro libro = prestamo.getLibro();
                Integer cantidad = solicitudesPorLibro.get(libro);
        
                if (cantidad == null) {
                    solicitudesPorLibro.put(libro, 1);
                    continue;
                }

                solicitudesPorLibro.put(libro, cantidad + 1);
            }
        }
        
        ArrayList<Entry<Libro, Integer>> prestamos = new ArrayList<>(); //guarda las solicitudes encontradas en un array

        for (Entry<Libro, Integer> e : solicitudesPorLibro.entrySet()){ prestamos.add(e); }

        prestamos.sort((a,b) -> b.getValue() - a.getValue()); //decreciente (b-a)

        int limite = Math.min(n, prestamos.size());
        for (int i=0; i<limite;i++){ resultado.addLast(prestamos.get(i).getKey()); }

        return resultado;
    }

    /**
     * Retorna todos los préstamos cuya fecha de vencimiento expiró
     * y que aún no fueron devueltos.
     * @param hoy fecha actual
     */
    public LinkedPositionalList<Prestamo> prestamosVencidos(LocalDate hoy) {
    if (hoy == null) { return new LinkedPositionalList<>(); }
        LinkedPositionalList<Prestamo> resultado = new LinkedPositionalList<>();

        for (LinkedPositionalList<Prestamo> listarActivos : prestamosActivos.values()) {
            for (Prestamo prestamo : listarActivos) {
                // NUEVO: Utiliza el método estaVencido() de Prestamo
                if (prestamo.estaVencido(hoy)) {
                    resultado.addLast(prestamo);
                }
            }
        }    

        return resultado;
    }
}