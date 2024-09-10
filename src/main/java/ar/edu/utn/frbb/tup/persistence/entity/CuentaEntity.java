package ar.edu.utn.frbb.tup.persistence.entity;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.TipoCuenta;
import ar.edu.utn.frbb.tup.model.TipoMoneda;
import java.time.LocalDateTime;

public class CuentaEntity extends BaseEntity{
    private final LocalDateTime fechaCreacion;
    private final long balance;
    private final String tipoCuenta;
    private final Long titular;
    private final long numeroCuenta;
    private String moneda;

    public CuentaEntity(Cuenta cuenta) {
        super(cuenta.getNumeroCuenta());
        this.numeroCuenta = cuenta.getNumeroCuenta();
        this.balance = cuenta.getBalance();
        this.tipoCuenta = cuenta.getTipoCuenta().toString();
        this.titular = cuenta.getTitular();
        this.fechaCreacion = cuenta.getFechaCreacion();
        this.moneda = cuenta.getMoneda().toString();
    }

    public Cuenta toCuenta() {
        Cuenta cuenta = new Cuenta();
        cuenta.setBalance(this.balance);
        cuenta.setNumeroCuenta(this.numeroCuenta);
        cuenta.setTipoCuenta(TipoCuenta.valueOf(this.tipoCuenta));
        cuenta.setFechaCreacion(this.fechaCreacion);
        cuenta.setTitular(this.titular);
        cuenta.setMoneda(TipoMoneda.valueOf(this.moneda));
        return cuenta;
    }

    public Long getTitular() {
        return titular;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }
}