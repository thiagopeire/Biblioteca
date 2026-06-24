package biblioteca.modelo;

import java.time.LocalDate;

public class Prestamo {
    private Socio     socio;
    private Libro     libro;
    private LocalDate fechaPrestamo;
    private LocalDate fechaVencimiento;
    private boolean   activo;

    public Prestamo(Socio socio, Libro libro, LocalDate fechaPrestamo, LocalDate fechaVencimiento) {
        this.socio=socio; 
        this.libro=libro; 
        this.fechaPrestamo=fechaPrestamo;
        this.activo=true;
        this.fechaVencimiento=fechaVencimiento;
    }

    public Socio getSocio(){
        return this.socio;
    }

    public Libro getLibro(){
        return this.libro;
    }

    public LocalDate getFechaPrestamo(){
        return this.fechaPrestamo;
    }

    public LocalDate getFechaVencimiento(){
        return this.fechaVencimiento;
    }

    public boolean estaActivo(){
        return this.activo;
    }

    /**
     * Actualiza el estado activo del préstamo.
     * Se usa en {@link biblioteca.logica.Logica#devolver} para marcarlo
     * como devuelto al momento de la devolución.
     * @param activo false para marcar el préstamo como devuelto
     */
    public void setActivo(boolean activo){
        this.activo = activo;
    }

    /**
     * Retorna true si el préstamo está activo y la fecha de vencimiento
     * es anterior a la fecha indicada.
     */
    public boolean estaVencido(LocalDate hoy) {
        return estaActivo() && this.fechaVencimiento.isBefore(hoy);
    }

    @Override
    public String toString() {
        return "Prestamo { Socio: " + socio.getNroSocio() +
                " | ISBN: " + libro.getIsbn() +
                " | Desde: " + fechaPrestamo +
                " | Vence: " + fechaVencimiento +
                " | Activo: " + activo + " }";
    }
}
