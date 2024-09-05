package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.model.*;
import ar.edu.utn.frbb.tup.model.exception.CuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.TipoDeCuentaNoSoportadaException;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.persistence.entity.CuentaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CuentaService {

    @Autowired
    private CuentaDao cuentaDao;

    @Autowired
    private ClienteService clienteService;

    public Cuenta darDeAltaCuenta(CuentaDto cuentaDto) throws CuentaAlreadyExistsException, TipoDeCuentaNoSoportadaException, TipoCuentaAlreadyExistsException {
        Cuenta cuenta = new Cuenta(cuentaDto);

        if(cuentaDao.find(cuenta.getNumeroCuenta()) != null) {
            throw new CuentaAlreadyExistsException("La cuenta " + cuenta.getNumeroCuenta() + " ya existe.");
        }

        if (!isTipoCuentaSupported(cuenta)) {
            throw new TipoDeCuentaNoSoportadaException("El tipo de cuenta " + cuenta.getTipoCuenta() + " no esta soportado.");
        }

        Cliente cliente = clienteService.buscarClientePorDni(cuentaDto.getDniTitular());
        clienteService.agregarCuenta(cuenta, cuentaDto.getDniTitular());
        cuenta.setTitular(cliente);

        cuentaDao.save(cuenta);

        return cuenta;
    }




    public Cuenta find(long id) {
        return cuentaDao.find(id);
    }

    private boolean isTipoCuentaSupported (Cuenta cuenta) {
        if (cuenta.getTipoCuenta().equals(TipoCuenta.CUENTA_CORRIENTE) && cuenta.getMoneda().equals(TipoMoneda.DOLARES)) {
            return false;
        }
        return true;
    }

    public boolean verificarCuenta(long numeroCliente, String moneda) {
        return cuentaDao.verificarCuenta(numeroCliente, moneda);
    }

    public void actualizarCuentaCliente(Prestamo prestamo) {
        long numeroCliente = prestamo.getNumeroCliente();
        double monto = prestamo.getMontoPrestamo();

        // Obtiene todas las cuentas del cliente
        List<Cuenta> cuentas = getCuentasByCliente(numeroCliente);

        // Encuentra la cuenta principal (o la primera cuenta) y actualiza su saldo
        if (!cuentas.isEmpty()) {
            Cuenta cuenta = cuentas.get(0); // Asumiendo que la primera cuenta es la principal
            cuenta.setBalance(cuenta.getBalance() + monto);
            cuentaDao.update(cuenta); // Actualiza la cuenta en la base de datos
        } else {
            throw new IllegalStateException("No se encontró una cuenta para el cliente " + numeroCliente);
        }
    }
    public List<Cuenta> getCuentasByCliente(long dni) {
        return cuentaDao.getCuentasByCliente(dni);
    }



}
