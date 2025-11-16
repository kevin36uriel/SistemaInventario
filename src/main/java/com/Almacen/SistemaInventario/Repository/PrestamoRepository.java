package com.Almacen.SistemaInventario.Repository;

import com.Almacen.SistemaInventario.Model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    Optional<Prestamo> findByNoControl(String noControl);
}
