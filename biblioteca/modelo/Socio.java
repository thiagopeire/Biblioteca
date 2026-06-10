package biblioteca.modelo;

public class Socio {

    private String nroSocio;
    private String nombre;
    private String apellido;
    private String email;
    private boolean activo;

    public Socio(String nroSocio, String nombre, String apellido,
                 String email, boolean activo) {
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
        // TODO: implementar - dos socios son iguales si tienen el mismo nroSocio
        return false;
    }
}
