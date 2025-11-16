package com.Almacen.SistemaInventario.Mapper;

import com.Almacen.SistemaInventario.DTO.EmpleadoDTO;
import com.Almacen.SistemaInventario.Model.Empleado;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UsuarioMapper.class})
public interface EmpleadoMapper {
    @Mapping(source = "usuario.id_Usuario", target = "usuarioId")
    @Mapping(source = "usuario", target = "usuario")
    EmpleadoDTO toDTO(Empleado empleado);

    @Mapping(source = "usuarioId", target = "usuario.id_Usuario")
    @Mapping(source = "usuario", target = "usuario")
    Empleado toEntity(EmpleadoDTO empleadoDTO);
}