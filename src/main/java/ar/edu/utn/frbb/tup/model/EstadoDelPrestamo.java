package ar.edu.utn.frbb.tup.model;

import ar.edu.utn.frbb.tup.model.exception.EstadoDePrestamoNoValido;

public enum EstadoDelPrestamo {
    APROBADO("A"),
    RECHAZADO("R");

    private final String codigo;

    EstadoDelPrestamo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public static EstadoDelPrestamo fromCodigo(String codigo) throws EstadoDePrestamoNoValido {
        for (EstadoDelPrestamo estado : values()) {
            if (estado.getCodigo().equals(codigo)) {
                return estado;
            }
        }
        throw new EstadoDePrestamoNoValido("Código de estado inválido: " + codigo);
    }
}