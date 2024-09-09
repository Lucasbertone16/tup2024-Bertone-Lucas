package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.controller.dto.TransaccionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.edu.utn.frbb.tup.persistence.TransaccionDao;
import java.util.List;

// Anotación que indica que esta clase es un servicio en la capa de lógica de negocio
@Service
public class TransaccionService {

    // Inyección de dependencia del repositorio de transacciones
    @Autowired
    private TransaccionDao transaccionDao;

    // Método para obtener las transacciones asociadas a un ID de cuenta específico
    public List<TransaccionDto> obtenerTransacciones(long cuentaId) {
        // Llama al método del repositorio para buscar transacciones por ID de cuenta
        return transaccionDao.findByCuentaId(cuentaId);
    }

    // Método para obtener todas las transacciones almacenadas
    public List<TransaccionDto> obtenerTodasTransacciones() {
        // Llama al método del repositorio para obtener todas las transacciones
        return transaccionDao.getAllTransacciones();
    }

    // Método para añadir una nueva transacción
    public void agregarTransaccion(TransaccionDto transaccionDTO) {
        // Llama al método del repositorio para guardar la nueva transacción
        transaccionDao.save(transaccionDTO);
    }
}