package ar.edu.utn.frbb.tup.model;

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

    public static EstadoDelPrestamo fromCodigo(String codigo) {
        for (EstadoDelPrestamo estado : values()) {
            if (estado.getCodigo().equals(codigo)) {
                return estado;
            }
        }
        throw new IllegalArgumentException("Código de estado inválido: " + codigo);
    }
}