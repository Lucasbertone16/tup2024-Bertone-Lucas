package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.persistence.entity.CuentaEntity;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CuentaRepo {

    // Lista que simula una base de datos en memoria para almacenar las cuentas
    private final List<CuentaEntity> cuentas = new ArrayList<>();

    public Optional<CuentaEntity> findById(long cuentaId) {
        // Busca en la lista de cuentas y retorna la primera cuenta que coincide con el ID proporcionado
        return cuentas.stream()
                .filter(cuenta -> cuenta.getNumeroCuenta() == cuentaId)
                .findFirst();
    }

    public void save(CuentaEntity cuentaEntity) {
        // Verifica si la cuenta ya existe, si existe, actualiza la información
        Optional<CuentaEntity> cuentaExistente = findById(cuentaEntity.getNumeroCuenta());
        if (cuentaExistente.isPresent()) {
            cuentas.remove(cuentaExistente.get());
        }
        // Añade la cuenta al repositorio (o la actualiza si ya existía)
        cuentas.add(cuentaEntity);
    }

    // Retorna una nueva lista que contiene todas las cuentas
    public List<CuentaEntity> findAll() {
        return new ArrayList<>(cuentas);
    }

    // Elimina de la lista cualquier cuenta que tenga el ID especificado
    public void deleteById(long cuentaId) {
        cuentas.removeIf(cuenta -> cuenta.getNumeroCuenta() == cuentaId);
    }

    // Verifica si una cuenta existe en el repositorio por su ID.
    public boolean existsById(long cuentaId) {
        return cuentas.stream()
                .anyMatch(cuenta -> cuenta.getNumeroCuenta() == cuentaId);
    }
}