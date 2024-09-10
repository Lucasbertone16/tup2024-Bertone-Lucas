package ar.edu.utn.frbb.tup.controller.validator;


import ar.edu.utn.frbb.tup.controller.dto.PrestamoDto;
import ar.edu.utn.frbb.tup.model.exception.MonedaNoSoportadaException;
import org.springframework.stereotype.Component;


@Component
public class PrestamoValidator {

    public void validate(PrestamoDto prestamoDto) throws Exception {
        if ((!"P".equals(prestamoDto.getMoneda()) && !"D".equals(prestamoDto.getMoneda()))) {
           throw new MonedaNoSoportadaException("El tipo de moneda no es correcto o es nulo");
        }
    }
}
