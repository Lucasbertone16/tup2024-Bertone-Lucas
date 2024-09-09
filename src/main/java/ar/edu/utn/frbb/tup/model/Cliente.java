package ar.edu.utn.frbb.tup.model;

import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.model.TipoCuenta;
import ar.edu.utn.frbb.tup.model.TipoMoneda;
import ar.edu.utn.frbb.tup.model.TipoPersona;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Cliente extends Persona{

    private TipoPersona tipoPersona;
    private String banco;
    private LocalDate fechaAlta;
    private Set<Cuenta> cuentas = new HashSet<>();
    private Set<Prestamo> prestamos = new HashSet<>();

    public Cliente() {
        super();
    }
    public Cliente(ClienteDto clienteDto) {
        super(clienteDto.getDni(), clienteDto.getApellido(), clienteDto.getNombre(), clienteDto.getFechaNacimiento());
        tipoPersona = TipoPersona.fromString(clienteDto.getTipoPersona()); //convierto en string el tipo de personas
        fechaAlta = LocalDate.now();
        banco = clienteDto.getBanco();
    }


    public TipoPersona getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(TipoPersona tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Set<Cuenta> getCuentas() {
        return cuentas;
    }

    public void addCuenta(Cuenta cuenta) {
        this.cuentas.add(cuenta);
        cuenta.setTitular(cuenta.getTitular());
    }

    public boolean tieneCuenta(TipoCuenta tipoCuenta, TipoMoneda moneda) {
        for (Cuenta cuenta:
                cuentas) {
            if (tipoCuenta.equals(cuenta.getTipoCuenta()) && moneda.equals(cuenta.getMoneda())) {
                return true;
            }
        }
        return false;
    }

    public boolean tieneCuentaMoneda(TipoMoneda moneda) {
        for (Cuenta cuenta:
                cuentas) {
            if (moneda.equals(cuenta.getMoneda())) {
                return true;
            }
        }
        return false;
    }

    public Set<Prestamo> getPrestamos() {
        return prestamos;
    }

    public void addPrestamo(Prestamo prestamo) {
        this.prestamos.add(prestamo);
        prestamo.setNumeroCliente(prestamo.getNumeroCliente());
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "tipoPersona=" + tipoPersona +
                ", banco='" + banco + '\'' +
                ", fechaAlta=" + fechaAlta +
                ", cuentas=" + cuentas +
                '}';
    }
}