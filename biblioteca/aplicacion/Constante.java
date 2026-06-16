package biblioteca.aplicacion;

import java.time.format.DateTimeFormatter;

public class Constante {
    public static final int OPCION_PRESTAR         = 1;
    public static final int OPCION_DEVOLVER        = 2;
    public static final int OPCION_BUSCAR_ISBN     = 3;
    public static final int OPCION_BUSCAR_TITULO   = 4;
    public static final int OPCION_BUSCAR_AUTOR    = 5;
    public static final int OPCION_DISPONIBLES     = 6;
    public static final int OPCION_PRESTAMOS_SOCIO = 7;
    public static final int OPCION_HISTORIAL       = 8;
    public static final int OPCION_RANKING         = 9;
    public static final int OPCION_VENCIDOS        = 10;
    public static final int OPCION_SALIR           = 0; 
    public static final int TOTAL_OPCIONES = 11;

    public static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy"); //esto permite reutilizarlo entre las capas de interfaz y datos.
}


