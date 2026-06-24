package biblioteca.datos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

import biblioteca.aplicacion.Constante;
import biblioteca.modelo.Libro;
import biblioteca.modelo.Prestamo;
import biblioteca.modelo.Socio;
import net.datastructures.LinkedPositionalList;
import net.datastructures.ProbeHashMap;

public class Dato {

    /**
     * Carga los libros desde un archivo de texto.
     * Formato de cada línea: isbn;titulo;autor;genero;anio;ejemplares
     * Ejemplo: 978-0;Cien años de soledad;García Márquez;Novela;1967;3
     *
     * @return mapa indexado por ISBN
     */
    public static ProbeHashMap<String, Libro> cargarLibros(String fileName) throws FileNotFoundException {
        ProbeHashMap<String, Libro> libros = new ProbeHashMap<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");

                String isbn = datos[0];
                String titulo = datos[1];
                String autor = datos[2];
                String genero = datos[3];
                int añoPubli = Integer.parseInt(datos[4]); 
                int uniDisp  = Integer.parseInt(datos[5]); 


                Libro libro = new Libro(isbn, titulo, autor, genero, añoPubli, uniDisp);

                libros.put(isbn, libro);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
            return null;
        }

        return libros;
    }

    /**
     * Carga los socios desde un archivo de texto.
     * Formato de cada línea: nroSocio;nombre;apellido;email;activo
     * Ejemplo: S001;Juan;Perez;juan@mail.com;true
     *
     * @return mapa indexado por nroSocio
     */
    public static ProbeHashMap<String, Socio> cargarSocios(String fileName) throws FileNotFoundException {
        ProbeHashMap<String, Socio> socios = new ProbeHashMap<>();    
         try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");

                String nroSocio = datos[0];
                String nombre = datos[1];
                String apellido = datos[2];
                String email = datos[3];
                boolean activo = Boolean.parseBoolean(datos[4]); 

                Socio socio = new Socio(nroSocio, nombre, apellido, email, activo);

                socios.put(nroSocio, socio);
            }

        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
            return null;
        }

        return socios;
    }

    /**
     * Carga los préstamos activos desde un archivo de texto.
     * Formato de cada línea: nroSocio;isbn;fechaPrestamo;fechaVencimiento
     * Ejemplo: S001;978-0;01/06/2026;15/06/2026
     *
     * @return mapa indexado por nroSocio con la lista de préstamos de cada socio
     */
   public static ProbeHashMap<String, LinkedPositionalList<Prestamo>> cargarPrestamos(
        String fileName,
        ProbeHashMap<String, Socio> socios,
        ProbeHashMap<String, Libro> libros)
        throws FileNotFoundException {
 
        ProbeHashMap<String, LinkedPositionalList<Prestamo>> prestamos = new ProbeHashMap<>();
 
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
 
            while ((line = br.readLine()) != null) {
                String[] datos = line.split(";");
 
                String nroSocio            = datos[0];
                String isbn                = datos[1];
                String fechaPrestamostr    = datos[2];
                String fechaVencimientostr = datos[3];
 
                //se valida que el socio y el libro existan antes de crear el préstamo
                Socio socio = socios.get(nroSocio);
                Libro libro = libros.get(isbn);
                if (socio == null || libro == null) continue;
 
                LocalDate fp = LocalDate.parse(fechaPrestamostr, Constante.FMT);
                LocalDate fv = LocalDate.parse(fechaVencimientostr, Constante.FMT);
 
                Prestamo p = new Prestamo(socio, libro, fp, fv);
 
                //se descuenta el ejemplar al cargar cada préstamo activo,
                //para que el stock refleje la realidad desde el inicio.
                libro.setEjemplaresDisponibles(libro.getEjemplaresDisp() - 1);
 
                LinkedPositionalList<Prestamo> listPrestamos = prestamos.get(nroSocio);
                if (listPrestamos == null) {
                    listPrestamos = new LinkedPositionalList<>();
                    prestamos.put(nroSocio, listPrestamos);
                }
                listPrestamos.addLast(p);
            }
 
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
            return null;
        }
 
        return prestamos;
    }
}
