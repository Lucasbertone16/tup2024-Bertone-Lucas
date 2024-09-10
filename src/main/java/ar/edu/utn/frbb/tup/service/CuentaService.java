package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.model.*;
import ar.edu.utn.frbb.tup.model.exception.*;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;

import ar.edu.utn.frbb.tup.persistence.entity.CuentaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Optional;

@Component
public class CuentaService {


    @Autowired
    private CuentaDao cuentaDao;

    @Autowired
    private ClienteService clienteService;

    public Cuenta darDeAltaCuenta(CuentaDto cuentaDto) throws CuentaAlreadyExistsException, TipoDeCuentaNoSoportadaException, Exception, ClienteNoEncontradoException {
        Cuenta cuenta = new Cuenta(cuentaDto);

        if(cuentaDao.find(cuenta.getNumeroCuenta()) != null) {
            throw new CuentaAlreadyExistsException("La cuenta " + cuenta.getNumeroCuenta() + " ya existe.");
        }

        if (!isTipoCuentaSupported(cuenta)) {
            throw new TipoDeCuentaNoSoportadaException("El tipo de cuenta " + cuenta.getTipoCuenta() + " no esta soportado.");
        }

        Cliente cliente = clienteService.buscarClientePorDni(cuentaDto.getDniTitular());
        clienteService.agregarCuenta(cuenta, cuentaDto.getDniTitular());


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



    public void actualizarCuenta(Prestamo prestamo) throws Exception {
        Cuenta cuenta = findByMoneda(prestamo.getMoneda());
        cuenta.setBalance(cuenta.getBalance() + prestamo.getMontoPedido());
        cuentaDao.save(cuenta);
    }

    public Cuenta findByMoneda(TipoMoneda moneda) throws Exception {
        if (cuentaDao.findByMoneda(moneda) == null) {
            throw new CuentaNoEncontradaException("La cuenta no existe");
        }
        return cuentaDao.findByMoneda(moneda);
    }

    public void  pagarCuotaPrestamo(Prestamo prestamo) throws Exception {
        Cuenta cuenta = findByMoneda(prestamo.getMoneda());
        if (cuenta.getBalance() < prestamo.getValorCuota()) {
            throw new NoSaldoException("No hay suficiente saldo en la cuenta");
        }
        cuenta.setBalance(cuenta.getBalance() - prestamo.getValorCuota());
        cuentaDao.save(cuenta);
    }

}
