package biblioteca.modelo;

public class Libro {

    private String isbn;
    private String titulo;
    private String autor;
    private String genero;
    private int anioPublicacion;
    private int ejemplaresDisponibles;

    public Libro(String isbn, String titulo, String autor, String genero,
                 int anioPublicacion, int ejemplaresDisponibles) {
        // TODO: inicializar atributos
    }

    // TODO: getters y setters

    @Override
    public String toString() {
        // TODO: implementar
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        // TODO: implementar - dos libros son iguales si tienen el mismo ISBN
        return false;
    }
}
