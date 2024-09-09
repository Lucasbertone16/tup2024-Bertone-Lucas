package ar.edu.utn.frbb.tup.controller.dto;

public class CuentaDto {

    private String tipoCuenta;
    private long dniTitular;
    private String moneda;
    private long numeroCuenta; // Añadido si deseas recibir o manejar el número de cuenta



    public long getDniTitular() {
        return dniTitular;
    }

    public String getMoneda() {
        return moneda;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public long getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setDniTitular(long dniTitular) {
        this.dniTitular = dniTitular;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public void setNumeroCuenta(long numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }
}
