package com.Almacen.SistemaInventario.Repository;

import com.Almacen.SistemaInventario.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsuario(String usuario);
    Optional<Usuario> findByUsuarioAndPassword(String usuario, String password);
    Optional<Usuario> findByCarrera(String carrera);
}
