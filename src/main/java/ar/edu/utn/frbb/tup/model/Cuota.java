package ar.edu.utn.frbb.tup.model;

public class Cuota {
    private int cuotaNro;
    private double cuotaMonto;

    public Cuota(int cuotaNro, double cuotaMonto) {
        this.cuotaNro = 1;
        this.cuotaMonto = (cuotaMonto/cuotaNro) * 1.05;
    }

    public int getCuotaNro() {
        return cuotaNro;
    }

    public void setCuotaNro(int cuotaNro) {
        this.cuotaNro = cuotaNro;
    }

    public double getCuotaMonto() {
        return cuotaMonto;
    }

    public void setCuotaMonto(double cuotaMonto) {
        this.cuotaMonto = cuotaMonto;
    }
}