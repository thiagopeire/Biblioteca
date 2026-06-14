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
    public String getIsbn(){
        return isbn;
    }

    public String getTitulo(){
        return this.titulo;
    }

    public String getAutor(){
        return this.autor;
    }

    public Integer getAñoPublicacion(){
        return this.anioPublicacion;
    }

    public String getGenero(){
        return this.genero;
    }

    public Integer getEjemplaresDisp(){
        return this.ejemplaresDisponibles;
    }
/**
     * Actualiza la cantidad de ejemplares disponibles.
     * Se usa en {@link biblioteca.logica.Logica#prestar} para decrementar
     * y en {@link biblioteca.logica.Logica#devolver} para incrementar.
     *
     * @param ejemplaresDisponibles nueva cantidad de ejemplares disponibles
     */
    public void setEjemplaresDisponibles(int ejemplaresDisponibles){
        this.ejemplaresDisponibles = ejemplaresDisponibles;
    }


    @Override
    public String toString() {
        return (
        "\nTitulo:                 " + getTitulo() +
        "\nAutor:                  " + getAutor() +
        "\nGenero:                 " + getGenero() +
        "\nAnio de Publicacion:    " + getAñoPublicacion() +
        "\nEjemplares Disponibles: " + getEjemplaresDisp());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Libro libro){
            return libro.isbn.equals(this.isbn);
        }
        return false;
    }
}
