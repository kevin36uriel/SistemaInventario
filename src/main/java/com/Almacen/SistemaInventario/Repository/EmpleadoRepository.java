package com.Almacen.SistemaInventario.Repository;

import com.Almacen.SistemaInventario.Model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado,Long> {
    Optional<Empleado> findByNombreAndApellidos(String nombre, String apellidos);
}
