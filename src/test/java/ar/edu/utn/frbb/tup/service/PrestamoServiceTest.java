package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.controller.dto.PrestamoDto;
import ar.edu.utn.frbb.tup.model.Prestamo;
import ar.edu.utn.frbb.tup.model.PrestamoResultado;
import ar.edu.utn.frbb.tup.model.EstadoDelPrestamo;
import ar.edu.utn.frbb.tup.model.TipoMoneda;
import ar.edu.utn.frbb.tup.model.exception.*;
import ar.edu.utn.frbb.tup.persistence.PrestamoDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PrestamoServiceTest {

    @Mock
    private ClienteService clienteService;

    @Mock
    private CuentaService cuentaService;

    @Mock
    private PrestamoDao prestamoDao;

    @Mock
    private ScoreCrediticioService scoreCreditService;

    @InjectMocks
    private PrestamoService prestamoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void darDeAltaPrestamoSucces() throws Exception, ClienteNoEncontradoException, CuentaNoEncontradaException, NumeroClienteNullPrestamoException, PLazoMesesMaxMixPrestamo, MontoMinimoPrestamoException {
        PrestamoDto prestamoDto = new PrestamoDto();
        prestamoDto.setNumeroCliente(4633967L);
        prestamoDto.setMontoPrestamo(20000);
        prestamoDto.setPlazoMeses(6);
        prestamoDto.setMoneda("P");

        when(scoreCreditService.verifyScore(4633967L)).thenReturn(true);
        doNothing().when(clienteService).agregarPrestamoCliente(any(Prestamo.class), anyLong());
        doNothing().when(cuentaService).actualizarCuenta(any(Prestamo.class));

        PrestamoResultado result = prestamoService.solicitarPrestamo(prestamoDto);

        assertNotNull(result);
        assertEquals(EstadoDelPrestamo.APROBADO, result.getEstado());
        assertEquals("El monto del préstamo fue acreditado en su cuenta", result.getMensaje());

        verify(prestamoDao).save(any(Prestamo.class));
    }

    @Test
    void prestamoRechazado() throws Exception, ClienteNoEncontradoException, CuentaNoEncontradaException, NumeroClienteNullPrestamoException, PLazoMesesMaxMixPrestamo, MontoMinimoPrestamoException {
        PrestamoDto prestamoDto = new PrestamoDto();
        prestamoDto.setNumeroCliente(4633967L);
        prestamoDto.setMontoPrestamo(1000L);
        prestamoDto.setPlazoMeses(12);
        prestamoDto.setMoneda("P");

        when(scoreCreditService.verifyScore(4633967L)).thenReturn(false);

        PrestamoResultado result = prestamoService.solicitarPrestamo(prestamoDto);

        assertNotNull(result);
        assertEquals(EstadoDelPrestamo.RECHAZADO, result.getEstado());
        assertEquals("El cliente no tiene un credito apto para solicitar un prestamo", result.getMensaje());

        verify(prestamoDao, never()).save(any(Prestamo.class));
    }

    @Test
    void getPrestamosByClienteSucces() throws ClienteNoEncontradoException, Exception, PrestamoNoExisteException {
        long dni = 4633967L;
        List<Prestamo> prestamos = Arrays.asList(
                new Prestamo(dni, 7, 1000L, TipoMoneda.PESOS),
                new Prestamo(dni, 20, 2000L, TipoMoneda.DOLARES)
        );

        when(clienteService.buscarClientePorDni(dni)).thenReturn(null); // Simulamos que el cliente existe
        when(prestamoDao.getPrestamosByCliente(dni)).thenReturn(prestamos);

        List<Prestamo> result = prestamoService.obtenerPrestamoPorDni(dni);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(dni, result.get(0).getNumeroCliente());
        assertEquals(dni, result.get(1).getNumeroCliente());
    }

    @Test
    void getPrestamosByClienteRechazado() throws ClienteNoEncontradoException, Exception {
        long dni = 4633967L;

        when(clienteService.buscarClientePorDni(dni)).thenThrow(new ClienteNoEncontradoException("El cliente no existe"));

        assertThrows(ClienteNoEncontradoException.class, () -> prestamoService.obtenerPrestamoPorDni(dni));
    }


    @Test
    void falloEnActualizacionDeCuenta() throws Exception, ClienteNoEncontradoException, CuentaNoEncontradaException {
        PrestamoDto prestamoDto = new PrestamoDto();
        prestamoDto.setNumeroCliente(4633967L);
        prestamoDto.setMontoPrestamo(1000L);
        prestamoDto.setPlazoMeses(6);
        prestamoDto.setMoneda("P");

        when(scoreCreditService.verifyScore(4633967L)).thenReturn(true);
        doNothing().when(clienteService).agregarPrestamoCliente(any(Prestamo.class), anyLong());
        doThrow(new RuntimeException("Error al actualizar la cuenta")).when(cuentaService).actualizarCuenta(any(Prestamo.class));

        Exception exception = assertThrows(RuntimeException.class, () -> prestamoService.solicitarPrestamo(prestamoDto));
        assertEquals("Error al actualizar la cuenta", exception.getMessage());

        verify(prestamoDao, never()).save(any(Prestamo.class));
    }

    @Test
    void montoMinimoPrestamoExcepcion() {
        PrestamoDto prestamoDto = new PrestamoDto();
        prestamoDto.setNumeroCliente(4633967L);
        prestamoDto.setMontoPrestamo(999L);  // Menor al mínimo permitido
        prestamoDto.setPlazoMeses(6);
        prestamoDto.setMoneda("P");

        MontoMinimoPrestamoException exception = assertThrows(MontoMinimoPrestamoException.class, () -> prestamoService.solicitarPrestamo(prestamoDto));
        assertEquals("El monto del préstamo debe ser mayor a 1000.", exception.getMessage());

        verify(prestamoDao, never()).save(any(Prestamo.class));
    }

    @Test
    void plazoMesesInvalido() {
        PrestamoDto prestamoDto = new PrestamoDto();
        prestamoDto.setNumeroCliente(4633967L);
        prestamoDto.setMontoPrestamo(20000L);
        prestamoDto.setPlazoMeses(2);  // Menor al mínimo permitido
        prestamoDto.setMoneda("P");

        PLazoMesesMaxMixPrestamo exception = assertThrows(PLazoMesesMaxMixPrestamo.class, () -> prestamoService.solicitarPrestamo(prestamoDto));
        assertEquals("El plazo debe estar entre 3 y 120 meses.", exception.getMessage());

        verify(prestamoDao, never()).save(any(Prestamo.class));
    }

    @Test
    void numeroClienteInvalido() {
        PrestamoDto prestamoDto = new PrestamoDto();
        prestamoDto.setNumeroCliente(0L);  // Número de cliente inválido
        prestamoDto.setMontoPrestamo(20000L);
        prestamoDto.setPlazoMeses(6);
        prestamoDto.setMoneda("P");

        NumeroClienteNullPrestamoException exception = assertThrows(NumeroClienteNullPrestamoException.class, () -> prestamoService.solicitarPrestamo(prestamoDto));
        assertEquals("El número de cliente no es válido.", exception.getMessage());

        verify(prestamoDao, never()).save(any(Prestamo.class));
    }

    @Test
    void getPrestamosByClienteSinPrestamos() throws ClienteNoEncontradoException, Exception {
        long dni = 29857643;

        // Simulamos que el cliente existe
        when(clienteService.buscarClientePorDni(dni)).thenReturn(null);

        // Simulamos que no tiene préstamos
        when(prestamoDao.getPrestamosByCliente(dni)).thenReturn(Collections.emptyList());

        // Verificamos que se lanza la excepción PrestamosNoEncontradosException
        PrestamoNoExisteException exception = assertThrows(PrestamoNoExisteException.class, () -> prestamoService.obtenerPrestamoPorDni(dni));

        // Validamos el mensaje de la excepción
        assertEquals("El cliente no tiene préstamos", exception.getMessage());
    }
}