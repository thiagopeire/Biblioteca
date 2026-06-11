//Para correr maven es necesario que el paquete sea: Interfaz
//De otra manera lo correcto sería dejarlo como: test.Interfaz
package Interfaz; 

import biblioteca.interfaz.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.ByteArrayInputStream;

public class TestInterfaz{
    @Test
    public void mostrarMenu_OpcionInvalida(){
        Interfaz.setScanner(new ByteArrayInputStream("20".getBytes())); //Opcion Invalida 
        Integer op = Interfaz.menu();
        if (op == -1){
            return;
        }
        fail("la opcion ingresada ("+ op.toString() +") debio ser rechazada.");
    }    

    @Test
    public void mostrarMenu_OpcionValida(){
        Interfaz.setScanner(new ByteArrayInputStream("1".getBytes())); //Opcion PRESTAR
        Integer op = Interfaz.menu();
        if (op == -1){
            fail("la opcion ingresada ("+ op.toString() +") debio ser aceptada.");
        }
    }    
}

