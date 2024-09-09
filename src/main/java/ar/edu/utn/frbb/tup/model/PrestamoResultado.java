package ar.edu.utn.frbb.tup.model;

import ar.edu.utn.frbb.tup.model.EstadoDelPrestamo;

public class PrestamoResultado {
    private EstadoDelPrestamo estado;
    private String mensaje;
    private Cuota cuota;

    public PrestamoResultado() {
    }
    public PrestamoResultado(EstadoDelPrestamo estado, String mensaje, Cuota cuota) {
        this.estado = estado;
        this.mensaje = mensaje;
        this.cuota = cuota;
    }

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

    public void setPlanPago(int cuotaNro, double cuotaMonto) {
        this.cuota = new Cuota(cuotaNro, cuotaMonto);
    }
}