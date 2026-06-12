package biblioteca.modelo;

public class Socio {

    private String nroSocio;
    private String nombre;
    private String apellido;
    private String email;
    private boolean activo;

    public Socio(String nroSocio, String nombre, String apellido,
                 String email, boolean activo) {
        this.nroSocio=nroSocio;
        this.nombre=nombre;
        this.apellido=apellido;
        this.email=email;
        this.activo=activo;
    }

    // TODO: getters y setters

    public String getNroSocio(){
        return nroSocio;
    }

    public boolean isActive(){
        return this.activo;
    }

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
