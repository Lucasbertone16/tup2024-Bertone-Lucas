package ar.edu.utn.frbb.tup.service;
import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.Prestamo;
import ar.edu.utn.frbb.tup.model.exception.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.EsMenorException;
import ar.edu.utn.frbb.tup.model.exception.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.model.exception.CuentaNoEncontradaException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    ClienteDao clienteDao;

    public ClienteService(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    public Cliente darDeAltaCliente(ClienteDto clienteDto) throws Exception, EsMenorException, ClienteAlreadyExistsException {
        Cliente cliente = new Cliente(clienteDto);

        if (clienteDao.find(cliente.getDni(), false) != null) {
            throw new ClienteAlreadyExistsException("El cliente ya existe");
        }

        if (cliente.getEdad() < 18) {
            throw new EsMenorException("El cliente debe ser mayor a 18 años");
        }

        clienteDao.save(cliente);
        return cliente;
    }

    public void agregarCuenta(Cuenta cuenta, long dniTitular) throws Exception, ClienteNoEncontradoException, TipoCuentaAlreadyExistsException {
        Cliente titular = buscarClientePorDni(dniTitular);
        cuenta.setTitular(titular.getDni());
        if (titular.tieneCuenta(cuenta.getTipoCuenta(), cuenta.getMoneda())) {
            throw new TipoCuentaAlreadyExistsException("El cliente ya posee una cuenta de ese tipo y moneda");
        }
        titular.addCuenta(cuenta);
        clienteDao.save(titular);
    }

    //Agregamos los prestamos al cliente con este metodo
    public void agregarPrestamoCliente(Prestamo prestamo, long dniTitular) throws Exception, ClienteNoEncontradoException, CuentaNoEncontradaException {
        Cliente titular = buscarClientePorDni(dniTitular);
        prestamo.setNumeroCliente(titular.getDni());
        if (!titular.tieneCuentaMoneda(prestamo.getMoneda())) {
            throw new CuentaNoEncontradaException("El cliente no posee una cuenta de esa moneda");
        }
        titular.addPrestamo(prestamo);
        clienteDao.save(titular);
    }

    public Cliente buscarClientePorDni(long dni) throws Exception, ClienteNoEncontradoException {
        Cliente cliente = clienteDao.find(dni, true);
        if(cliente == null) {
            throw new ClienteNoEncontradoException("El cliente no existe");
        }
        return cliente;
    }
}