package ar.edu.utn.frbb.tup.model;

public class PrestamoResultado {
    private EstadoDelPrestamo estado;
    private String mensaje;
    private Cuota cuota;

    //Constructor vacio
    public PrestamoResultado() {
    }

    //Constructor
    public PrestamoResultado(EstadoDelPrestamo estado, String mensaje, Cuota cuota) {
        this.estado = estado;
        this.mensaje = mensaje;
        this.cuota = cuota;
    }

    //Getters y Setters
    public EstadoDelPrestamo getEstado() {
        return estado;
    }

    public void setEstado(EstadoDelPrestamo estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Cuota getPlanPago() {
        return cuota;
    }

    // Setter que permite crear y asignar una nueva cuota con el número de cuota y monto especificados
    public void setPlanPago(int cuotaNro, double cuotaMonto) {
        this.cuota = new Cuota(cuotaNro, cuotaMonto);
    }
}