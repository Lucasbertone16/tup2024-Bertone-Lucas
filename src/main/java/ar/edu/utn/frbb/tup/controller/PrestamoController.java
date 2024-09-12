package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.controller.dto.PrestamoDto;
import ar.edu.utn.frbb.tup.model.Prestamo;
import ar.edu.utn.frbb.tup.model.exception.PrestamoNoExisteException;
import ar.edu.utn.frbb.tup.controller.validator.PrestamoValidator;
import ar.edu.utn.frbb.tup.model.PrestamoResultado;
import ar.edu.utn.frbb.tup.model.exception.*;
import ar.edu.utn.frbb.tup.service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/prestamo")
public class PrestamoController {

    @Autowired
    private PrestamoValidator prestamoValidator;

    @Autowired
    private PrestamoService prestamoService;

    @GetMapping("/{dni}")
    public List<Prestamo> buscarPrestamoPorDni(@PathVariable long dni) {
        try {
            return prestamoService.obtenerPrestamoPorDni(dni);
        } catch (ClienteNoEncontradoException e) {
            // Maneja el caso de cliente no encontrado devolviendo 404
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }catch (PrestamoNoExisteException e) {
            // Prestamo no encontrado devolviendo 404
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
        catch (Exception e) {
            // Manejo genérico de errores devolviendo 500
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado", e);
        }
    }

    @PostMapping
    public PrestamoResultado crearPrestamo(@RequestBody PrestamoDto prestamodto) {
        try {
            prestamoValidator.validate(prestamodto);
            return prestamoService.solicitarPrestamo(prestamodto);
        } catch (ClienteNoEncontradoException e) {
            // Cliente no encontrado - devolver 404
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (MonedaNoSoportadaException e) {
            // Moneda no soportada - devolver 400
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (CuentaNoEncontradaException e) {
            // Cuenta no encontrada - devolver 404
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (PLazoMesesMaxMixPrestamo e) {
            // Plazo de meses fuera del rango permitido - devolver 400
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (MontoMinimoPrestamoException e) {
            // Monto mínimo del préstamo no cumplido - devolver 400
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (NumeroClienteNullPrestamoException e) {
            // Número de cliente inválido o nulo - devolver 400
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (Exception e) {
            // Manejo genérico de errores devolviendo 500
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado", e);
        }
    }
}
