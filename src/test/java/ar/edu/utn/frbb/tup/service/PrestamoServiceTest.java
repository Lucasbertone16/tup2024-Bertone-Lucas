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

        // Configurar el comportamiento del servicio de verificación de score crediticio
        when(scoreCreditService.verifyScore(4633967L)).thenReturn(true); // Simular que el score crediticio es positivo

        // Configurar el comportamiento del servicio de cliente para agregar un préstamo
        doNothing().when(clienteService).agregarPrestamoCliente(any(Prestamo.class), anyLong()); // Simular que el préstamo se agrega sin errores

        // Configurar el comportamiento del servicio de cuenta para actualizar la cuenta
        doNothing().when(cuentaService).actualizarCuenta(any(Prestamo.class)); // Simular que la cuenta se actualiza sin errores

        // Llamar al método solicitarPrestamo del servicio de préstamos con el dto creado
        PrestamoResultado result = prestamoService.solicitarPrestamo(prestamoDto);

        // Verificar que el resultado no es nulo
        assertNotNull(result);
        // Verificar que el estado del préstamo es "APROBADO"
        assertEquals(EstadoDelPrestamo.APROBADO, result.getEstado());
        // Verificar que el mensaje de resultado es el esperado
        assertEquals("Monto acreditado a su cuenta!", result.getMensaje());

        // Verificar que el método save del DAO de préstamos fue llamado con cualquier objeto Prestamo
        verify(prestamoDao).save(any(Prestamo.class));
    }

    @Test
    void prestamoRechazado() throws Exception, ClienteNoEncontradoException, CuentaNoEncontradaException, NumeroClienteNullPrestamoException, PLazoMesesMaxMixPrestamo, MontoMinimoPrestamoException {
        PrestamoDto prestamoDto = new PrestamoDto();
        prestamoDto.setNumeroCliente(4633967L);
        prestamoDto.setMontoPrestamo(10000L);
        prestamoDto.setPlazoMeses(12);
        prestamoDto.setMoneda("P");

        // Configurar el comportamiento del servicio de verificación de score crediticio
        when(scoreCreditService.verifyScore(4633967L)).thenReturn(false); // Simular que el score crediticio es negativo

        // Llamar al método solicitarPrestamo del servicio de préstamos con el dto creado
        PrestamoResultado result = prestamoService.solicitarPrestamo(prestamoDto);

        // Verificar que el resultado no es nulo
        assertNotNull(result);
        // Verificar que el estado del préstamo es "RECHAZADO"
        assertEquals(EstadoDelPrestamo.RECHAZADO, result.getEstado());
        // Verificar que el mensaje de resultado es el esperado
        assertEquals("No cuenta con la puntuacion adecuada para ser beneficiario del prestamo", result.getMensaje());

        // Verificar que el método save del DAO de préstamos NO fue llamado
        verify(prestamoDao, never()).save(any(Prestamo.class));
    }

    @Test
    void getPrestamosByClienteSucces() throws ClienteNoEncontradoException, Exception, PrestamoNoExisteException {
        long dni = 4633967L;
        // Crear una lista de préstamos que se espera que el cliente tenga
        List<Prestamo> prestamos = Arrays.asList(
                new Prestamo(dni, 7, 1000L, TipoMoneda.PESOS), // Primer préstamo
                new Prestamo(dni, 20, 2000L, TipoMoneda.DOLARES) // Segundo préstamo
        );

        // Configurar el comportamiento del servicio mockeado
        // Simular que el cliente con el DNI especificado sí existe
        when(clienteService.buscarClientePorDni(dni)).thenReturn(null);
        // Simular que el DAO devuelve la lista de préstamos para el cliente
        when(prestamoDao.getPrestamosByCliente(dni)).thenReturn(prestamos);

        // Llamar al método que se está probando
        List<Prestamo> result = prestamoService.obtenerPrestamoPorDni(dni);

        // Verificar que el resultado no es nulo
        assertNotNull(result);
        // Verificar que la lista de préstamos tiene el tamaño esperado
        assertEquals(2, result.size());
        // Verificar que el primer préstamo en la lista tiene el DNI del cliente
        assertEquals(dni, result.get(0).getNumeroCliente());
        // Verificar que el segundo préstamo en la lista también tiene el DNI del cliente
        assertEquals(dni, result.get(1).getNumeroCliente());
    }

    @Test
    void getPrestamosByClienteRechazado() throws ClienteNoEncontradoException, Exception {
        long dni = 4633967L;

        // Configurar el comportamiento del servicio mockeado
        // Simular que el cliente con el DNI especificado no existe (retorna null)
        when(clienteService.buscarClientePorDni(dni)).thenReturn(null);

        // Verificar que al intentar obtener los préstamos para un cliente inexistente
        // se lanza una excepción de tipo PrestamoNoExisteException
        assertThrows(PrestamoNoExisteException.class, () -> prestamoService.obtenerPrestamoPorDni(dni));
    }


    @Test
    void falloEnActualizacionDeCuenta() throws Exception, ClienteNoEncontradoException, CuentaNoEncontradaException {
        PrestamoDto prestamoDto = new PrestamoDto();
        prestamoDto.setNumeroCliente(4633967L);
        prestamoDto.setMontoPrestamo(10000L);
        prestamoDto.setPlazoMeses(6);
        prestamoDto.setMoneda("P");

        // Configurar el comportamiento del mock para el servicio de verificación de score
        when(scoreCreditService.verifyScore(4633967L)).thenReturn(true);

        // Configurar el comportamiento de los mocks para el servicio de cliente y cuenta
        doNothing().when(clienteService).agregarPrestamoCliente(any(Prestamo.class), anyLong());
        // Simular una excepción al intentar actualizar la cuenta
        doThrow(new RuntimeException("Error al actualizar la cuenta")).when(cuentaService).actualizarCuenta(any(Prestamo.class));

        // Ejecutar el método solicitarPrestamo y verificar que se lanza una excepción
        Exception exception = assertThrows(RuntimeException.class, () -> prestamoService.solicitarPrestamo(prestamoDto));
        // Verificar que el mensaje de la excepción es el esperado
        assertEquals("Error al actualizar la cuenta", exception.getMessage());

        // Verificar que el método save de prestamoDao no fue llamado
        verify(prestamoDao, never()).save(any(Prestamo.class));
    }

    @Test
    void montoMinimoPrestamoExcepcion() {
        PrestamoDto prestamoDto = new PrestamoDto();
        prestamoDto.setNumeroCliente(4633967L);
        prestamoDto.setMontoPrestamo(999L);  // Menor al mínimo permitido
        prestamoDto.setPlazoMeses(6);
        prestamoDto.setMoneda("P");

        // Ejecutar el método solicitarPrestamo y verificar que se lanza una excepción
        MontoMinimoPrestamoException exception = assertThrows(MontoMinimoPrestamoException.class, () -> prestamoService.solicitarPrestamo(prestamoDto));
        // Verificar que el mensaje de la excepción es el esperado
        assertEquals("El monto del préstamo debe ser mayor a 1000.", exception.getMessage());

        // Verificar que el método save de prestamoDao no fue llamado
        verify(prestamoDao, never()).save(any(Prestamo.class));
    }

    @Test
    void plazoMesesInvalido() {
        PrestamoDto prestamoDto = new PrestamoDto();
        prestamoDto.setNumeroCliente(4633967L);
        prestamoDto.setMontoPrestamo(20000L);
        prestamoDto.setPlazoMeses(2);  // Menor al mínimo permitido
        prestamoDto.setMoneda("P");

        // Ejecutar el método solicitarPrestamo y verificar que se lanza una excepción
        PLazoMesesMaxMixPrestamo exception = assertThrows(PLazoMesesMaxMixPrestamo.class, () -> prestamoService.solicitarPrestamo(prestamoDto));
        // Verificar que el mensaje de la excepción es el esperado
        assertEquals("El plazo debe estar entre 3 y 120 meses.", exception.getMessage());

        // Verificar que el método save de prestamoDao no fue llamado
        verify(prestamoDao, never()).save(any(Prestamo.class));
    }

    @Test
    void numeroClienteInvalido() {
        PrestamoDto prestamoDto = new PrestamoDto();
        prestamoDto.setNumeroCliente(0L);  // Número de cliente inválido
        prestamoDto.setMontoPrestamo(20000L);
        prestamoDto.setPlazoMeses(6);
        prestamoDto.setMoneda("P");

        // Ejecutar el método solicitarPrestamo y verificar que se lanza una excepción
        NumeroClienteNullPrestamoException exception = assertThrows(NumeroClienteNullPrestamoException.class, () -> prestamoService.solicitarPrestamo(prestamoDto));
        // Verificar que el mensaje de la excepción es el esperado
        assertEquals("El número de cliente no es válido.", exception.getMessage());

        // Verificar que el método save de prestamoDao no fue llamado
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