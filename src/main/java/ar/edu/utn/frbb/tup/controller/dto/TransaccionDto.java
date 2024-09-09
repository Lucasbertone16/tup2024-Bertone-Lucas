package ar.edu.utn.frbb.tup.controller.dto;

import java.time.LocalDate;

public class TransaccionDto {
    private LocalDate fecha; // Fecha de la transacción
    private String tipo; // Tipo de la transacción (por ejemplo, "Débito", "Crédito")
    private String descripcionBreve; // Descripción breve de la transacción
    private double monto; // Monto de la transacción
    private long cuentaId; // ID de la cuenta asociada a la transacción

    // Constructor con parámetros para inicializar todos los atributos
    public TransaccionDto(LocalDate fecha, String tipo, String descripcionBreve, double monto, long cuentaId) {
        this.fecha = fecha; // Establece la fecha de la transacción
        this.tipo = tipo; // Establece el tipo de la transacción
        this.descripcionBreve = descripcionBreve; // Establece la descripción breve de la transacción
        this.monto = monto; // Establece el monto de la transacción
        this.cuentaId = cuentaId; // Establece el ID de la cuenta asociada
    }

    // Getter para obtener la fecha de la transacción
    public LocalDate getFecha() {
        return fecha;
    }

    // Setter para establecer la fecha de la transacción
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    // Getter para obtener el tipo de la transacción
    public String getTipo() {
        return tipo;
    }

    // Setter para establecer el tipo de la transacción
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

    // Getter para obtener el ID de la cuenta asociada a la transacción
    public long getCuentaId() {
        return cuentaId;
    }

    // Setter para establecer el ID de la cuenta asociada a la transacción
    public void setCuentaId(long cuentaId) {
        this.cuentaId = cuentaId;
    }
}