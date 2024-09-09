package ar.edu.utn.frbb.tup.controller.dto;

public class PrestamoDto {
    private long numeroCliente;
    private int plazoMeses;
    private long montoPrestamo;
    private String moneda;

    public PrestamoDto() {
    }

    public PrestamoDto(long numeroCliente, int plazoMeses, long montoPrestamo, String moneda) {
        this.numeroCliente = numeroCliente;
        this.plazoMeses = plazoMeses;
        this.montoPrestamo = montoPrestamo;
        this.moneda = moneda;
    }

    public long getNumeroCliente() {
        return numeroCliente;
    }

    public void setNumeroCliente(long numeroCliente) {
        this.numeroCliente = numeroCliente;
    }

    public int getPlazoMeses() {
        return plazoMeses;
    }

    public void setPlazoMeses(int plazoMeses) {
        this.plazoMeses = plazoMeses;
    }

    public long getMontoPrestamo() {
        return montoPrestamo;
    }

    public void setMontoPrestamo(long montoPrestamo) {
        this.montoPrestamo = montoPrestamo;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }
}