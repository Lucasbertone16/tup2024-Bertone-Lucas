package ar.edu.utn.frbb.tup.controller.validator;

import ar.edu.utn.frbb.tup.controller.dto.PrestamoDto;
import ar.edu.utn.frbb.tup.model.exception.MonedaNoSoportadaException;
import org.springframework.stereotype.Component;

@Component
public class PrestamoValidator {

    // Método que valida los datos del préstamo antes de proceder
    public void validate(PrestamoDto prestamoDto) throws MonedaNoSoportadaException {
        // Validar que la moneda sea "P" o "D"
        if ((!"P".equals(prestamoDto.getMoneda()) && !"D".equals(prestamoDto.getMoneda()))) {
            throw new MonedaNoSoportadaException("El tipo de moneda no es correcto o es nulo");
        }
        //Check de monto mayor a 0
        if (prestamoDto.getMontoPrestamo() <= 0) {
            throw new IllegalArgumentException("El monto del préstamo debe ser mayor a 0.");
        }
        //Check de plazo de meses
        if (prestamoDto.getPlazoMeses() < 3 || prestamoDto.getPlazoMeses() > 120) {
            throw new IllegalArgumentException("El plazo debe estar entre 3 y 120 meses.");
        }
    }
}
