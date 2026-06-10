package test.Interfaz;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import biblioteca.interfaz.*;

public class TestInterfaz{
    static Interfaz i = new Interfaz();
    @Test
    public void mostrarMenu(){
        Integer op = Interfaz.menu();
        Interfaz.mostrarMensaje(op.toString());
    }
}