package com.Almacen.SistemaInventario.Service;

import com.Almacen.SistemaInventario.DTO.UsuarioDTO;
import com.Almacen.SistemaInventario.Mapper.UsuarioMapper;
import com.Almacen.SistemaInventario.Model.Usuario;
import com.Almacen.SistemaInventario.Repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    public List<UsuarioDTO> getUsuarios() {
        return usuarioRepository.findAll().stream().map(usuarioMapper::toDTO).collect(Collectors.toList());
    }

    public Optional<UsuarioDTO> getUsuarioPorUsername(String username) {
        return usuarioRepository.findByUsuario(username).map(usuarioMapper::toDTO);
    }


    @Transactional
    public UsuarioDTO saveUsuario(UsuarioDTO usuarioDTO) {
        if (usuarioDTO.getUsuario() == null || usuarioDTO.getPassword() == null
                || usuarioDTO.getRol() == null || usuarioDTO.getArea() == null || usuarioDTO.getEmpleadoId() == null) {
            throw new IllegalArgumentException("El usuario, contraseña, rol y empleadoId son obligatorios");
        }
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        @SuppressWarnings("null")
        Usuario guardado = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(guardado);
    }

    @Transactional
    public Optional<UsuarioDTO> updateUsuario(String username, UsuarioDTO usuarioDTO) {
        return usuarioRepository.findByUsuario(username)
                .map(usuario -> {
                    if (usuarioDTO.getUsuario() == null || usuarioDTO.getPassword() == null
                            || usuarioDTO.getRol() == null || usuarioDTO.getEmpleadoId() == null) {
                        throw new IllegalArgumentException("El usuario, contraseña, rol y empleadoId son obligatorios");
                    }
                    usuario.setUsuario(usuarioDTO.getUsuario());
                    usuario.setPassword(usuarioDTO.getPassword());
                    usuario.setRol(usuarioDTO.getRol());
                    usuario.setCarrera(usuarioDTO.getCarrera());
                    usuario.setArea(usuarioDTO.getArea());
                    usuario.getEmpleado().setId_Empleado(usuarioDTO.getEmpleadoId());
                    Usuario actualizado = usuarioRepository.save(usuario);
                    return usuarioMapper.toDTO(actualizado);
                });
    }

    @Transactional
    public void deleteUsuario(String username) {
        Optional<Usuario> usuarioExiste = usuarioRepository.findByUsuario(username);
        usuarioExiste.ifPresent(usuarioRepository::delete);
    }
}