//Para correr con maven es necesario que el paquete sea: Interfaz
//De otra manera lo correcto sería dejarlo como: test.Interfaz
package test.Interfaz; 

import biblioteca.interfaz.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

public class TestInterfaz{
    @Test
    public void mostrarMenu_OpcionInvalida(){
        Interfaz.definirScanner(new ByteArrayInputStream("20\n".getBytes())); //Opcion Invalida 
        Integer op = Interfaz.menu();
        if (op == -1){

            return;
        }
        fail("la opcion ingresada ("+ op.toString() +") debio ser rechazada.");
    }    

    @Test
    public void mostrarMenu_OpcionValida(){
        Interfaz.definirScanner(new ByteArrayInputStream("1\n".getBytes())); //Opcion PRESTAR
        Integer op = Interfaz.menu();
        if (op == -1){
            fail("la opcion ingresada ("+ op.toString() +") debio ser aceptada.");
        }
    }    

    @Test
    public void pedirFecha_FechaValida(){
        Interfaz.definirScanner(new ByteArrayInputStream("10/10/2000".getBytes()));
        if (Interfaz.pedirFecha("Fecha de cumpleaños") == null){
            fail("Valor Esperado: LocalDate. Valor Recibido: null");
        }
    }

    @Test
    public void pedirFecha_FechaInvalida(){
        Interfaz.definirScanner(new ByteArrayInputStream("50/10/2000".getBytes()));
        if (Interfaz.pedirFecha("Fecha de cumpleaños") != null){
            fail("Valor Esperado: null. Valor Recibido: LocalDate");
        }
    }
}

