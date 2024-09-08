package ar.edu.utn.frbb.tup.controller.dto;

import ar.edu.utn.frbb.tup.controller.dto.TransaccionDto;

import java.util.List;

public class CtaTransaccionDto {
    private long numeroCuenta;
    private List<TransaccionDto> transacciones;

    // Getters y Setters
    public long getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(long numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public List<TransaccionDto> getTransacciones() {
        return transacciones;
    }

    public void setTransacciones(List<TransaccionDto> transacciones) {
        this.transacciones = transacciones;
    }
}
