package ar.edu.utn.frbb.tup.model;

import java.time.LocalDate;

public class Transaccion {
    private LocalDate fecha; // Fecha en la que se realizó la transacción
    private String tipo; // Tipo de transacción, como "DEPOSITO", "RETIRO", etc.
    private String descripcionBreve; // Descripción breve de la transacción
    private double monto; // Monto de dinero involucrado en la transacción
    private long cuentaId; // ID de la cuenta asociada con la transacción

    // Constructor para inicializar los atributos de la transacción
    public Transaccion(LocalDate fecha, String tipo, String descripcionBreve, double monto, long cuentaId) {
        this.fecha = fecha;
        this.tipo = tipo;
        this.descripcionBreve = descripcionBreve;
        this.monto = monto;
        this.cuentaId = cuentaId;
    }

    // Getter para obtener la fecha de la transacción
    public LocalDate getFecha() {
        return fecha;
    }

    // Setter para establecer la fecha de la transacción
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    // Getter para obtener el tipo de transacción
    public String getTipo() {
        return tipo;
    }

    // Setter para establecer el tipo de transacción
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    // Getter para obtener la descripción breve de la transacción
    public String getDescripcionBreve() {
        return descripcionBreve;
    }

    // Setter para establecer la descripción breve de la transacción
    public void setDescripcionBreve(String descripcionBreve) {
        this.descripcionBreve = descripcionBreve;
    }

    // Getter para obtener el monto de la transacción
    public double getMonto() {
        return monto;
    }

    // Setter para establecer el monto de la transacción
    public void setMonto(double monto) {
        this.monto = monto;
    }

    // Getter para obtener el ID de la cuenta asociada con la transacción
    public long getCuentaId() {
        return cuentaId;
    }

    // Setter para establecer el ID de la cuenta asociada con la transacción
    public void setCuentaId(long cuentaId) {
        this.cuentaId = cuentaId;
    }
}