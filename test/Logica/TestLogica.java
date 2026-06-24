package Logica;

import java.time.LocalDate;

import biblioteca.logica.Logica;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import biblioteca.modelo.Libro;
import biblioteca.modelo.Prestamo;
import biblioteca.modelo.Socio;
import net.datastructures.LinkedPositionalList;
import net.datastructures.ProbeHashMap;

public class TestLogica {
    Logica logica;

    public void configurar(int ejemplaresDisp, boolean socioActivo) {
        ProbeHashMap<String, Libro> catalogo = new ProbeHashMap<>();
        ProbeHashMap<String, Socio> socios = new ProbeHashMap<>();
        ProbeHashMap<String, LinkedPositionalList<Prestamo>> prestamosActivos = new ProbeHashMap<>();

        Libro l = new Libro("978-84-376-0419-0", "Rayuela", "Julio Cortázar", "Novela", 1963, ejemplaresDisp);
        Socio s = new Socio("S005", "Luis", "Fernandez", "luis.fernandez@mail.com", socioActivo);
        Socio s1 = new Socio("S006", "Luis", "Hernando", "luis.hernando@mail.com", socioActivo);

        catalogo.put(l.getIsbn(), l);
        socios.put(s.getNroSocio(), s);
        socios.put(s1.getNroSocio(), s1);

        this.logica = new Logica(catalogo, socios, prestamosActivos);
    }

    // ══════════════════════════════════════════════════════════════
    //  OPCION 1 - PRESTAR
    // ══════════════════════════════════════════════════════════════

    @Test
    public void prestar_Exito() {
        configurar(3, true);
        assertTrue(logica.prestar("S005", "978-84-376-0419-0"));
    }

    @Test
    public void prestar_Fallo_NoEjemDisp() {
        configurar(0, true);
        assertFalse(logica.prestar("S005", "978-84-376-0419-0"));
    }

    @Test
    public void prestar_Fallo_SocioInactivo() {
        configurar(1, false);
        assertFalse(logica.prestar("S005", "978-84-376-0419-0"));
    }

    @Test
    public void prestar_Fallo_SocioNoExiste() {
        configurar(1, true);
        assertFalse(logica.prestar("S999", "978-84-376-0419-0"));
    }

    @Test
    public void prestar_Fallo_LibroNoExiste() {
        configurar(1, true);
        assertFalse(logica.prestar("S005", "000-00-000-0"));
    }

    @Test
    public void prestar_VerificarDescuentoEjemplares() {
        configurar(3, true);
        logica.prestar("S005", "978-84-376-0419-0");
        Libro libro = logica.buscarPorIsbn("978-84-376-0419-0");
        assertEquals(2, libro.getEjemplaresDisp());
    }

    // ══════════════════════════════════════════════════════════════
    //  OPCION 2 - DEVOLVER
    // ══════════════════════════════════════════════════════════════

    @Test
    public void devolver_Exito() {
        configurar(3, true);
        logica.prestar("S005", "978-84-376-0419-0");
        assertTrue(logica.devolver("S005", "978-84-376-0419-0"));
    }

    @Test
    public void devolver_VerificarRestaEjemplares() {
        configurar(3, true);
        logica.prestar("S005", "978-84-376-0419-0");
        logica.devolver("S005", "978-84-376-0419-0");
        Libro libro = logica.buscarPorIsbn("978-84-376-0419-0");
        assertEquals(3, libro.getEjemplaresDisp());
    }

    @Test
    public void devolver_VerificarEstadoPrestamo() {
        configurar(3, true);
        logica.prestar("S005", "978-84-376-0419-0");
        logica.devolver("S005", "978-84-376-0419-0");
        LinkedPositionalList<Prestamo> activos = logica.prestamosActivosDeSocio("S005");
        assertEquals(0, contarElementos(activos));
    }

    @Test
    public void devolver_Fallo_SocioSinPrestamos() {
        configurar(3, true);
        assertFalse(logica.devolver("S005", "978-84-376-0419-0"));
    }

    @Test
    public void devolver_Fallo_LibroNoPrestado() {
        configurar(3, true);
        logica.prestar("S005", "978-84-376-0419-0");
        assertFalse(logica.devolver("S005", "000-00-000-0"));
    }

    // ══════════════════════════════════════════════════════════════
    //  OPCION 3 - BUSCAR POR ISBN
    // ══════════════════════════════════════════════════════════════

    @Test
    public void buscarPorIsbn_Exito() {
        configurar(3, true);
        Libro libro = logica.buscarPorIsbn("978-84-376-0419-0");
        assertNotNull(libro);
        assertEquals("Rayuela", libro.getTitulo());
    }

    @Test
    public void buscarPorIsbn_NoEncontrado() {
        configurar(3, true);
        assertNull(logica.buscarPorIsbn("000-00-000-0"));
    }

    // ══════════════════════════════════════════════════════════════
    //  OPCION 4 - BUSCAR POR TITULO
    // ══════════════════════════════════════════════════════════════

    @Test
    public void buscarPorTitulo_Exito() {
        configurar(3, true);
        LinkedPositionalList<Libro> resultado = logica.buscarPorTitulo("Rayuela");
        assertFalse(resultado.isEmpty());
    }

    @Test
    public void buscarPorTitulo_CasoInsensitive() {
        configurar(3, true);
        LinkedPositionalList<Libro> resultado = logica.buscarPorTitulo("rayuela");
        assertFalse(resultado.isEmpty());
    }

    @Test
    public void buscarPorTitulo_NoEncontrado() {
        configurar(3, true);
        LinkedPositionalList<Libro> resultado = logica.buscarPorTitulo("LibroInexistente");
        assertTrue(resultado.isEmpty());
    }

    // ══════════════════════════════════════════════════════════════
    //  OPCION 5 - BUSCAR POR AUTOR
    // ══════════════════════════════════════════════════════════════

    @Test
    public void buscarPorAutor_Exito() {
        configurar(3, true);
        LinkedPositionalList<Libro> resultado = logica.buscarPorAutor("Cortázar");
        assertFalse(resultado.isEmpty());
    }

    @Test
    public void buscarPorAutor_CasoInsensitive() {
        configurar(3, true);
        LinkedPositionalList<Libro> resultado = logica.buscarPorAutor("cortázar");
        assertFalse(resultado.isEmpty());
    }

    @Test
    public void buscarPorAutor_NoEncontrado() {
        configurar(3, true);
        LinkedPositionalList<Libro> resultado = logica.buscarPorAutor("AutorInexistente");
        assertTrue(resultado.isEmpty());
    }

    // ══════════════════════════════════════════════════════════════
    //  OPCION 6 - LISTAR DISPONIBLES
    // ══════════════════════════════════════════════════════════════

    @Test
    public void listarDisponibles_ConDisponibles() {
        configurar(3, true);
        LinkedPositionalList<Libro> resultado = logica.listarDisponibles();
        assertFalse(resultado.isEmpty());
    }

    @Test
    public void listarDisponibles_SinDisponibles() {
        configurar(0, true);
        LinkedPositionalList<Libro> resultado = logica.listarDisponibles();
        assertTrue(resultado.isEmpty());
    }

    // ══════════════════════════════════════════════════════════════
    //  OPCION 7 - PRESTAMOS ACTIVOS DE SOCIO
    // ══════════════════════════════════════════════════════════════

    @Test
    public void prestamosActivosDeSocio_ConPrestamos() {
        configurar(3, true);
        logica.prestar("S005", "978-84-376-0419-0");
        LinkedPositionalList<Prestamo> resultado = logica.prestamosActivosDeSocio("S005");
        assertEquals(1, contarElementos(resultado));
    }

    @Test
    public void prestamosActivosDeSocio_SinPrestamos() {
        configurar(3, true);
        LinkedPositionalList<Prestamo> resultado = logica.prestamosActivosDeSocio("S005");
        assertTrue(resultado.isEmpty());
    }

    @Test
    public void prestamosActivosDeSocio_SocioNoExiste() {
        configurar(3, true);
        LinkedPositionalList<Prestamo> resultado = logica.prestamosActivosDeSocio("S999");
        assertTrue(resultado.isEmpty());
    }

    // ══════════════════════════════════════════════════════════════
    //  OPCION 8 - HISTORIAL DE SOCIO
    // ══════════════════════════════════════════════════════════════

    @Test
    public void historialDeSocio_ConHistorial() {
        configurar(5, true);
        logica.prestar("S005", "978-84-376-0419-0");
        LinkedPositionalList<Prestamo> historial = logica.historialDeSocio("S005");
        assertFalse(historial.isEmpty());
    }

    @Test
    public void historialDeSocio_SinHistorial() {
        configurar(3, true);
        LinkedPositionalList<Prestamo> historial = logica.historialDeSocio("S005");
        assertTrue(historial.isEmpty());
    }

    // ══════════════════════════════════════════════════════════════
    //  OPCION 9 - LIBROS MAS SOLICITADOS
    // ══════════════════════════════════════════════════════════════

    @Test
    public void librosMasSolicitados_Exito() {
        configurar(5, true);
        logica.prestar("S005", "978-84-376-0419-0");
        LinkedPositionalList<Libro> masSolicitados = logica.librosMasSolicitados(1);
        assertEquals(1, contarElementos(masSolicitados));
    }

    @Test
    public void librosMasSolicitados_SinHistorial() {
        configurar(5, true);
        LinkedPositionalList<Libro> masSolicitados = logica.librosMasSolicitados(1);
        assertTrue(masSolicitados.isEmpty());
    }

    // ══════════════════════════════════════════════════════════════
    //  OPCION 10 - PRESTAMOS VENCIDOS
    // ══════════════════════════════════════════════════════════════

    @Test
    public void prestamosVencidos_ConVencidos() {
        configurar(3, true);
        logica.prestar("S005", "978-84-376-0419-0");
        LocalDate fechaFuturo = LocalDate.now().plusDays(30);
        LinkedPositionalList<Prestamo> vencidos = logica.prestamosVencidos(fechaFuturo);
        assertFalse(vencidos.isEmpty());
    }

    @Test
    public void prestamosVencidos_SinVencidos() {
        configurar(3, true);
        logica.prestar("S005", "978-84-376-0419-0");
        LocalDate hoy = LocalDate.now();
        LinkedPositionalList<Prestamo> vencidos = logica.prestamosVencidos(hoy);
        assertTrue(vencidos.isEmpty());
    }

    // ══════════════════════════════════════════════════════════════
    //  UTILIDAD
    // ══════════════════════════════════════════════════════════════

    private int contarElementos(Iterable<?> iterable) {
        int contador = 0;
        for (Object elem : iterable) {
            contador++;
        }
        return contador;
    }
}
