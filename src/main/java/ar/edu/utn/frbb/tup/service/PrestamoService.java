package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.controller.dto.PrestamoDto;
import ar.edu.utn.frbb.tup.model.Prestamo;
import ar.edu.utn.frbb.tup.model.PrestamoResultado;
import ar.edu.utn.frbb.tup.model.EstadoDelPrestamo;
import ar.edu.utn.frbb.tup.model.exception.*;
import ar.edu.utn.frbb.tup.persistence.PrestamoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrestamoService {
    @Autowired
    private ClienteService clienteService;

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private PrestamoDao prestamoDao;

    @Autowired
    private ScoreCrediticioService scoreCreditService;


    public PrestamoResultado solicitarPrestamo (PrestamoDto prestamoDto) throws Exception, ClienteNoEncontradoException, CuentaNoEncontradaException, NumeroClienteNullPrestamoException, PLazoMesesMaxMixPrestamo, MontoMinimoPrestamoException {
        Prestamo prestamo = new Prestamo(prestamoDto);

        if (prestamoDto.getNumeroCliente() <= 0) {
            throw new NumeroClienteNullPrestamoException("El número de cliente no es válido.");
        }

        if (prestamoDto.getPlazoMeses() < 3 || prestamoDto.getPlazoMeses() > 120) {
            throw new PLazoMesesMaxMixPrestamo("El plazo debe estar entre 3 y 120 meses.");
        }

        if (prestamoDto.getMontoPrestamo()<= 1000){
            throw new MontoMinimoPrestamoException("El monto del préstamo debe ser mayor a 1000.");
        }

        //if por si el ScoreCrediticioService queda en false
        if (!scoreCreditService.verifyScore(prestamo.getNumeroCliente())) {
            PrestamoResultado prestamoResultado = new PrestamoResultado();
            prestamoResultado.setEstado(EstadoDelPrestamo.RECHAZADO);
            prestamoResultado.setMensaje("No cuenta con la puntuacion adecuada para ser beneficiario del prestamo");
            return prestamoResultado;
        }

        //guardamos el prestamo
        clienteService.agregarPrestamoCliente(prestamo, prestamo.getNumeroCliente());
        cuentaService.actualizarCuenta(prestamo);
        prestamoDao.save(prestamo);

        //Mensaje de aprobacion
        PrestamoResultado prestamoResultado = new PrestamoResultado();
        prestamoResultado.setEstado(EstadoDelPrestamo.APROBADO);
        prestamoResultado.setMensaje("Monto acreditado a su cuenta!");
        prestamoResultado.setPlanPago(prestamo.getPlazoMeses(), prestamo.getMontoPedido());
        return prestamoResultado;
    }

    //Buscamos los prestamos que tiene la persona por dni
    public List<Prestamo> obtenerPrestamoPorDni(long dni) throws Exception, ClienteNoEncontradoException, PrestamoNoExisteException {
        List<Prestamo> prestamos = prestamoDao.getPrestamosByCliente(dni);
        if (prestamos.isEmpty()) {
            // Lanzar una excepción si no tiene préstamos
            throw new PrestamoNoExisteException("El cliente no tiene préstamos");
        }
        clienteService.buscarClientePorDni(dni);
        return prestamoDao.getPrestamosByCliente(dni);
    }

}