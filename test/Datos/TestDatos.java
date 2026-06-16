package test.Datos;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileNotFoundException;

import biblioteca.datos.*;
import biblioteca.modelo.*;
import net.datastructures.*;


public class TestDatos {
    ProbeHashMap<String, Libro> libros = new ProbeHashMap<>();
    ProbeHashMap<String, Socio> socios = new ProbeHashMap<>();  
    ProbeHashMap<String, LinkedPositionalList<Prestamo>> prestamos = new ProbeHashMap<>();
        
    String librosPath = "libros.txt";
    String PrestamosPath = "prestamos.txt";
    String sociosPath = "socios.txt";

    @Test
    public void TestCargarLibros(){
        
        try {this.libros = Dato.cargarLibros(this.librosPath);}
        catch(FileNotFoundException e){fail("Fallo la carga de archivos: "+ e.getLocalizedMessage());}
        
        for (Entry<String, Libro> elem : libros.entrySet()) {
            System.out.println(elem.getValue().toString());
        }
    }

    @Test
    public void TestCargarSocios(){
        ProbeHashMap<String, Socio> socios = new ProbeHashMap<>();    
        try {this.socios = Dato.cargarSocios(this.sociosPath);}
        catch(FileNotFoundException e){fail("Fallo la carga de archivos: "+ e.getLocalizedMessage());}
        
        for (Entry<String, Libro> elem : libros.entrySet()) {
            System.out.println(elem.getValue().toString());
        }
    }

     @Test
    public void TestCargarPrestamos(){
        try {this.prestamos = Dato.cargarPrestamos(this.PrestamosPath, socios, libros);}
        catch(FileNotFoundException e){fail("Fallo la carga de archivos: "+ e.getLocalizedMessage());}
        
        for (Entry<String, Libro> elem : libros.entrySet()) {
            System.out.println(elem.getValue().toString());
        }
    }

    
}
