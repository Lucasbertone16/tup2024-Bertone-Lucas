package ar.edu.utn.frbb.tup.controller.validator;

import ar.edu.utn.frbb.tup.controller.dto.PrestamoDto;
import ar.edu.utn.frbb.tup.model.exception.MonedaNoSoportadaException;
import ar.edu.utn.frbb.tup.model.exception.MontoMinimoPrestamoException;
import ar.edu.utn.frbb.tup.model.exception.NumeroClienteNullPrestamoException;
import ar.edu.utn.frbb.tup.model.exception.PLazoMesesMaxMixPrestamo;
import org.springframework.stereotype.Component;

@Component
public class PrestamoValidator {

    // Método que valida los datos del préstamo antes de proceder
    public void validate(PrestamoDto prestamoDto) throws MonedaNoSoportadaException, NumeroClienteNullPrestamoException, PLazoMesesMaxMixPrestamo, MontoMinimoPrestamoException {

        // Validar que la moneda sea "P" o "D"
        if ((!"P".equals(prestamoDto.getMoneda()) && !"D".equals(prestamoDto.getMoneda()))) {
            throw new MonedaNoSoportadaException("El tipo de moneda no es correcto o es nulo");
        }

        //Monto minimo de prestamo 1000 para que sea mas realista
        if (prestamoDto.getMontoPrestamo() <= 1000) {
            throw new MontoMinimoPrestamoException("El monto del préstamo debe ser mayor a 0.");
        }

        //Check de plazo de meses
        if (prestamoDto.getPlazoMeses() < 3 || prestamoDto.getPlazoMeses() > 120) {
            throw new PLazoMesesMaxMixPrestamo("El plazo debe estar entre 3 y 120 meses.");
        }

        // Validar que el número de dni del cliente no sea null o 0
        if (prestamoDto.getNumeroCliente() <= 0) {
            throw new NumeroClienteNullPrestamoException("El número de cliente no es válido.");
        }
    }
}
