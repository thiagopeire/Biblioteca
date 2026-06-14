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

      //getter agregados: Nombre, Apellido, Email
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

    /**
 * Retorna una representación textual del socio con todos sus datos.
 * Agregado: implementación del método, antes estaba vacío (TODO).
 *
 * @return cadena con los datos del socio
 */
    @Override
    public String toString() {
        // TODO: implementar
        return "Socio #" + nroSocio +
           " | " + nombre + " " + apellido +
           " | " + email +
           " | Activo: " + activo;
    }

    /**
 * Dos socios son iguales si tienen el mismo número de socio.
 * Agregado: implementación del método, antes estaba vacío (TODO).
 *
 * @param obj objeto a comparar
 * @return true si ambos socios tienen el mismo nroSocio
 */
    @Override
    public boolean equals(Object obj) {
        // TODO: implementar - dos socios son iguales si tienen el mismo nroSocio
        if (obj instanceof Socio socio) {
    		return socio.nroSocio.equals(this.nroSocio);
    	}
        return false; 
    }
}
