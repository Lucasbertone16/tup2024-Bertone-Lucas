package ar.edu.utn.frbb.tup.model;

import ar.edu.utn.frbb.tup.model.exception.EstadoDePrestamoNoValido;
//Enum para marcar el estado del prestamo ya sea aprobado o rechazado
public enum EstadoDelPrestamo {
    APROBADO("A"),
    RECHAZADO("R");

    // Código asociado a cada estado (A para aprobado, R para rechazado)
    private final String codigo;

    //Constructor
    EstadoDelPrestamo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    // Convierte un código en el estado correspondiente, o lanza una excepción si es inválido
    public static EstadoDelPrestamo fromCodigo(String codigo) throws EstadoDePrestamoNoValido {
        for (EstadoDelPrestamo estado : values()) {
            if (estado.getCodigo().equals(codigo)) {
                return estado;
            }
        }
        throw new EstadoDePrestamoNoValido("Código de estado inválido: " + codigo);
    }
}