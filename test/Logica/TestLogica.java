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
        ProbeHashMap<String,LinkedPositionalList<Prestamo>> prestamosActivos = new ProbeHashMap<>();
        LinkedPositionalList<Prestamo> prestamos = new LinkedPositionalList<>();

        //objetos para testear
        Libro l = new Libro("978-84-376-0419-0", "Rayuela", "Julio Cortázar", "Novela", 1963, EjDisp);
        Socio s = new Socio("S005", "Luis", "Fernandez", "luis.fernandez@mail.com", SocioActivo);
        
        catalogo.put(l.getIsbn(), l);
        socios.put(s.getNroSocio(), s);

        
        prestamos.addFirst(new Prestamo(s, l, LocalDate.now(), LocalDate.now().plusDays(14)));
        prestamosActivos.put("p1", prestamos);

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
        }
    }
}
