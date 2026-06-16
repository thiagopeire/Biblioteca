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


    public String getNroSocio(){
        return nroSocio;
    }

    public String getNombre(){
        return nombre;
    }

    public String getApellido(){
        return apellido;
    }

    public String getEmail(){
        return email;
    }

    public boolean isActive(){
        return this.activo;
    }

    public void setEmail(String s) {
        this.email = s;
    }

    public void setApellido(String s) {
        this.apellido = s;
    }

    public void setNombre(String s) {
        this.nombre = s;
    }

    public void setNroSocio(String s) {
        this.nroSocio = s;
    }

    public void setActivo(boolean a){
        this.activo = a;
    }

    /**
 * Retorna una representación textual del socio con todos sus datos.
 *
 * @return cadena con los datos del socio
 */
    @Override
    public String toString() {
        return "Socio #" + nroSocio +
           " | " + nombre + " " + apellido +
           " | " + email +
           " | Activo: " + activo;
    }

    /**
 * Dos socios son iguales si tienen el mismo número de socio.
 *
 * @param obj objeto a comparar
 * @return true si ambos socios tienen el mismo nroSocio
 */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Socio socio) {
    		return socio.nroSocio.equals(this.nroSocio);
    	}
        return false; 
    }
}
