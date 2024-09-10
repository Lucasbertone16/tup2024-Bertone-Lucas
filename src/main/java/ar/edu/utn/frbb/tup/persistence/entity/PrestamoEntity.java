package ar.edu.utn.frbb.tup.persistence.entity;
import ar.edu.utn.frbb.tup.model.Prestamo;
import ar.edu.utn.frbb.tup.model.TipoMoneda;

public class PrestamoEntity extends BaseEntity {
    private final long numeroCliente;
    private final int plazoMeses;
    private final long montoPedido;
    private String moneda;
    private final long montoConIntereses;
    private final long saldoRestante; //saldo deudor a pagar
    private final long valorCuota;
    private final int cuotasPagas;

    public PrestamoEntity(Prestamo prestamo) {
        super(prestamo.getId());
        this.numeroCliente = prestamo.getNumeroCliente();
        this.plazoMeses = prestamo.getPlazoMeses();
        this.montoPedido = prestamo.getMontoPedido();
        this.moneda = prestamo.getMoneda().toString();
        this.montoConIntereses = prestamo.getMontoConIntereses();
        this.saldoRestante = prestamo.getSaldoRestante();
        this.valorCuota = prestamo.getValorCuota();
        this.cuotasPagas = prestamo.getCuotasPagas();
    }

    public Prestamo toPrestamo() {
        Prestamo prestamo = new Prestamo();
        prestamo.setId(this.getId());
        prestamo.setNumeroCliente(this.getNumeroCliente());
        prestamo.setPlazoMeses(this.getPlazoMeses());
        prestamo.setMontoPedido(this.getMontoPedido());
        prestamo.setMoneda(TipoMoneda.valueOf(this.getMoneda()));
        prestamo.setMontoConIntereses(this.getMontoConIntereses());
        prestamo.setSaldoRestante(this.getSaldoRestante());
        prestamo.setValorCuota(this.getValorCuota());
        prestamo.setCuotasPagas(this.getCuotasPagas());
        return prestamo;
    }

    public long getNumeroCliente() {
        return numeroCliente;
    }

    public int getPlazoMeses() {
        return plazoMeses;
    }


    public long getMontoPedido() {
        return montoPedido;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public long getMontoConIntereses() {
        return montoConIntereses;
    }


    public long getSaldoRestante() {
        return saldoRestante;
    }

    public long getValorCuota() {
        return valorCuota;
    }

    public int getCuotasPagas() {
        return cuotasPagas;
    }

}