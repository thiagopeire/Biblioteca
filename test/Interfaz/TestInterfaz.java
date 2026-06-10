package test.Interfaz;

import biblioteca.interfaz.*;
import org.junit.jupiter.api.Test;

public class TestInterfaz{
    static Interfaz i = new Interfaz();
    @Test
    public void mostrarMenu(){
        Integer op = Interfaz.menu();
        Interfaz.mostrarMensaje(op.toString());

    }
}