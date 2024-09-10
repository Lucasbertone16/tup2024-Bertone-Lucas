package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.controller.dto.PrestamoDto;
import ar.edu.utn.frbb.tup.model.Prestamo;
import ar.edu.utn.frbb.tup.controller.validator.PrestamoValidator;
import ar.edu.utn.frbb.tup.model.PrestamoResultado;
import ar.edu.utn.frbb.tup.model.exception.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/prestamo")
public class PrestamoController {

    @Autowired
    private PrestamoValidator prestamoValidator;

    @Autowired
    private PrestamoService prestamoService;

    @PostMapping
    public PrestamoResultado crearPrestamo(@RequestBody PrestamoDto prestamodto) throws Exception, ClienteNoEncontradoException {
        prestamoValidator.validate(prestamodto);
        return prestamoService.solicitarPrestamo(prestamodto);
    }

    @GetMapping("/{dni}")
    public List<Prestamo> buscarPrestamoPorDni(@PathVariable long dni) throws Exception, ClienteNoEncontradoException {
        return prestamoService.getPrestamosByCliente(dni);
    }


}