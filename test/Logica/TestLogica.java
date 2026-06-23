//Para correr con maven es necesario que el paquete sea: Interfaz
//De otra manera lo correcto sería dejarlo como: test.Interfaz
package Logica;

import java.time.LocalDate;

import biblioteca.logica.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.fail;

import biblioteca.modelo.Libro;
import biblioteca.modelo.Prestamo;
import biblioteca.modelo.Socio;
import net.datastructures.LinkedPositionalList;
import net.datastructures.ProbeHashMap;

public class TestLogica {
    Logica logica;

    public void setVariables(Integer EjDisp, boolean SocioActivo){
        //declaracion de mapas para construir logica 
        ProbeHashMap<String, Libro> catalogo = new  ProbeHashMap<>();
        
        ProbeHashMap<String, Socio> socios = new  ProbeHashMap<>();
        
        LinkedPositionalList<Prestamo> prestamos = new LinkedPositionalList<>(); //lista de prestamos
        
        ProbeHashMap<String,LinkedPositionalList<Prestamo>> prestamosActivos = new ProbeHashMap<>();

        //objetos para testear
        Libro l = new Libro("978-84-376-0419-0", "Rayuela", "Julio Cortázar", "Novela", 1963, EjDisp);
        Socio s = new Socio("S005", "Luis", "Fernandez", "luis.fernandez@mail.com", SocioActivo);
        Socio s1 = new Socio("S006", "Luis", "Hernando", "luis.hernando@mail.com", SocioActivo);

        Prestamo p = new Prestamo(s, l, LocalDate.now(), LocalDate.now().plusDays(14));
        Prestamo p1 = new Prestamo(s1, l, LocalDate.now(), LocalDate.now().plusDays(14));
        
        catalogo.put(l.getIsbn(), l);

        socios.put(s.getNroSocio(), s);
        socios.put(s1.getNroSocio(), s1);
        
        prestamos.addFirst(p);
        prestamos.addFirst(p1);

        prestamosActivos.put(s.getNroSocio(), prestamos);
        prestamosActivos.put(s1.getNroSocio(), prestamos);

        this.logica = new Logica(catalogo, socios, prestamosActivos);
    }

    @Test
    public void prestar_Exito(){
        setVariables(3, true);
        if (!logica.prestar("S005", "978-84-376-0419-0")){
            fail("fallo la funcion de prestamo inesperadamente");
        }
    }

    @Test
    public void prestar_Fallo_NoEjemDisp(){
        setVariables(0, true);
        if (logica.prestar("S005", "978-84-376-0419-0")){
            fail("No fallo la funcion de prestamo inesperadamente");
        }
    }

    @Test
    public void prestar_Fallo_SocioInactivo(){
        setVariables(1, false);
        if (logica.prestar("S005", "978-84-376-0419-0")){
            fail("No fallo la funcion de prestamo inesperadamente");
        }new Prestamo(s, l, LocalDate.now(), LocalDate.now().plusDays(14)
    }

    @Test
    public void librosMasSolicitados_Exito(){
        LinkedPositionalList<Libro> MasSolicitados = logica.librosMasSolicitados(2);
        setVariables(5, true);
        for (Libro elem : MasSolicitados) {
            elem.toString();
        }
    }
}
