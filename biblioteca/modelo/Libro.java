package biblioteca.modelo;

public class Libro {

    private String isbn;
    private String titulo;
    private String autor;
    private String genero;
    private int anioPublicacion;
    private int ejemplaresDisponibles;

    public Libro(String isbn, String titulo, String autor, String genero, int anioPublicacion, int ejemplaresDisponibles) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.anioPublicacion = anioPublicacion;
        this.ejemplaresDisponibles = ejemplaresDisponibles;
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
