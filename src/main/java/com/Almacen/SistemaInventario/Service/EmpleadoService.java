package com.Almacen.SistemaInventario.Service;

import com.Almacen.SistemaInventario.DTO.EmpleadoDTO;
import com.Almacen.SistemaInventario.Mapper.EmpleadoMapper;
import com.Almacen.SistemaInventario.Model.Empleado;
import com.Almacen.SistemaInventario.Model.Usuario;
import com.Almacen.SistemaInventario.Repository.EmpleadoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private EmpleadoMapper empleadoMapper;

    public List<EmpleadoDTO> getEmpleado() {
        return empleadoRepository.findAll().stream().map(empleadoMapper::toDTO).collect(Collectors.toList());
    }

    public Optional<EmpleadoDTO> getEmpleadoEspecifico(String nombre, String apellido) {
        return empleadoRepository.findByNombreAndApellidos(nombre, apellido).map(empleadoMapper::toDTO);
    }

    @Transactional
    public EmpleadoDTO saveEmpleado(EmpleadoDTO empleadoDTO) {
        if (empleadoDTO.getUsuario() == null || empleadoDTO.getUsuario().getUsuario() == null
                || empleadoDTO.getUsuario().getPassword() == null || empleadoDTO.getUsuario().getRol() == null || empleadoDTO.getArea() == null) {
            throw new IllegalArgumentException("El usuario, su contraseña y rol son obligatorios");
        }
        Empleado empleado = empleadoMapper.toEntity(empleadoDTO);
        @SuppressWarnings("null")
        Empleado guardado = empleadoRepository.save(empleado);
        return empleadoMapper.toDTO(guardado);
    }

    @Transactional
    public Optional<EmpleadoDTO> updateEmpleado(String nombre, String apellido, EmpleadoDTO empleadoDTO) {
        return empleadoRepository.findByNombreAndApellidos(nombre, apellido)
                .map(empleado -> {
                    if (empleadoDTO.getNombre() != null) {
                        empleado.setNombre(empleadoDTO.getNombre());
                    }
                    if (empleadoDTO.getApellidos() != null) {
                        empleado.setApellidos(empleadoDTO.getApellidos());
                    }
                    if (empleadoDTO.getRol() != null) {
                        empleado.setRol(empleadoDTO.getRol());
                    }
                    if(empleadoDTO.getCarrera() != null){
                        empleado.setCarrera(empleadoDTO.getCarrera());
                    }
                    if(empleadoDTO.getArea() != null){
                        empleado.setArea(empleadoDTO.getArea());
                    }
                    if (empleadoDTO.getTelefono() != null) {
                        empleado.setTelefono(empleadoDTO.getTelefono());
                    }
                    if (empleadoDTO.getEmail() != null) {
                        empleado.setEmail(empleadoDTO.getEmail());
                    }
                    if (empleadoDTO.getUsuario() != null) {
                        if (empleadoDTO.getUsuario().getUsuario() == null
                                || empleadoDTO.getUsuario().getPassword() == null
                                || empleadoDTO.getUsuario().getRol() == null) {
                            throw new IllegalArgumentException("El usuario, su contraseña y rol son obligatorios");
                        }
                        Usuario usuario = empleado.getUsuario();
                        if (usuario == null) {
                            usuario = new Usuario();
                            empleado.setUsuario(usuario);
                            usuario.setEmpleado(empleado);
                        }
                        usuario.setUsuario(empleadoDTO.getUsuario().getUsuario());
                        usuario.setPassword(empleadoDTO.getUsuario().getPassword());
                        usuario.setRol(empleadoDTO.getUsuario().getRol());
                        usuario.setCarrera(empleadoDTO.getCarrera());
                        usuario.setArea(empleadoDTO.getArea());

                    }
                    @SuppressWarnings("null")
                    Empleado empleadoActualizado = empleadoRepository.save(empleado);
                    return empleadoMapper.toDTO(empleadoActualizado);
                });
    }

    @SuppressWarnings("null")
    @Transactional
    public boolean deleteEmpleado(String nombre, String apellido) {
        Optional<Empleado> empleadoExiste = empleadoRepository.findByNombreAndApellidos(nombre, apellido);
        if(empleadoExiste.isPresent()){
            empleadoRepository.delete(empleadoExiste.get());
            return true;
        }
        return false;
    }

    @SuppressWarnings("null")
    public Optional<EmpleadoDTO> getById(Long id){
        return empleadoRepository.findById(id).map(empleadoMapper::toDTO);
    }
}