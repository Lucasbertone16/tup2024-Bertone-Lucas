package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.controller.dto.PrestamoDto;
import ar.edu.utn.frbb.tup.model.Prestamo;
import ar.edu.utn.frbb.tup.model.PrestamoResultado;
import ar.edu.utn.frbb.tup.model.EstadoDelPrestamo;
import ar.edu.utn.frbb.tup.model.exception.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.model.exception.CuentaNoEncontradaException;
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


    public PrestamoResultado solicitarPrestamo (PrestamoDto prestamoDto) throws Exception, ClienteNoEncontradoException, CuentaNoEncontradaException {
        Prestamo prestamo = new Prestamo(prestamoDto);
        //if por si el ScoreCrediticioService queda en false
        if (!scoreCreditService.verifyScore(prestamo.getNumeroCliente())) {
            PrestamoResultado prestamoResultado = new PrestamoResultado();
            prestamoResultado.setEstado(EstadoDelPrestamo.RECHAZADO);
            prestamoResultado.setMensaje("No cuenta con la puntuacion adecuada para ser beneficiario del prestamo");
            return prestamoResultado;
        }

        //guardamos el prestamo
        clienteService.agregarPrestamo(prestamo, prestamo.getNumeroCliente());
        cuentaService.actualizarCuenta(prestamo);
        prestamoDao.save(prestamo);

        //Mensaje de aprobacion
        PrestamoResultado prestamoResultado = new PrestamoResultado();
        prestamoResultado.setEstado(EstadoDelPrestamo.APROBADO);
        prestamoResultado.setMensaje("Monto acreditado a su cuenta!");
        prestamoResultado.setPlanPago(prestamo.getPlazoMeses(), prestamo.getMontoPedido());
        return prestamoResultado;
    }

    public List<Prestamo> getPrestamosByCliente(long dni) throws Exception, ClienteNoEncontradoException {
        clienteService.buscarClientePorDni(dni);
        return prestamoDao.getPrestamosByCliente(dni);
    }

}