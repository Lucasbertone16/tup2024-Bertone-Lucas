package ar.edu.utn.frbb.tup.persistence;

import org.springframework.stereotype.Repository;
import ar.edu.utn.frbb.tup.persistence.entity.TransaccionEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Anotación que indica que esta clase es un repositorio para la persistencia de datos
@Repository
public class TransaccionRepo {

    // Lista para almacenar transacciones en memoria usando la entidad TransaccionEntity
    private final List<TransaccionEntity> transacciones = new ArrayList<>();

    // Método para buscar transacciones por ID de cuenta
    public List<TransaccionEntity> findByCuentaId(long cuentaId) {
        // Filtrar las transacciones que coincidan con el ID de cuenta usando streams
        return transacciones.stream()
                .filter(t -> t.getCuentaId() == cuentaId) // Filtra las transacciones por ID de cuenta
                .collect(Collectors.toList()); // Colecciona las transacciones filtradas en una nueva lista
    }

    // Método para obtener todas las transacciones almacenadas
    public List<TransaccionEntity> getAllTransacciones() {
        // Devolver una nueva lista que contiene todas las transacciones actuales
        return new ArrayList<>(transacciones);
    }

    // Método para añadir una nueva transacción
    public void addTransaccion(TransaccionEntity transaccion) {
        // Añadir la transacción a la lista de transacciones
        transacciones.add(transaccion);
        // Imprimir un mensaje de confirmación en la consola (en producción, se debería usar un logger en lugar de System.out.println)
        System.out.println("Transacción añadida: " + transaccion);
    }
}