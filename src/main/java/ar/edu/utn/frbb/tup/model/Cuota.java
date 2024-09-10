package ar.edu.utn.frbb.tup.model;

public class Cuota {
    private int cuotaNro;
    private double cuotaMonto;

    //Constructor que inicializa el num de cuotas y el monto con interes
    public Cuota(int cuotaNro, double cuotaMonto) {
        //Asigna valor 1 como inicial
        this.cuotaNro = 1;
        // Se distribuye el monto entre las cuotas y se aplica un 5% de interés
        this.cuotaMonto = (cuotaMonto / cuotaNro) * 1.05;
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