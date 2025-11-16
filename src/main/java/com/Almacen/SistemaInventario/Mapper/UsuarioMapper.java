package com.Almacen.SistemaInventario.Mapper;

import com.Almacen.SistemaInventario.DTO.UsuarioDTO;
import com.Almacen.SistemaInventario.Model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(source = "empleado.id_Empleado", target = "empleadoId")
    UsuarioDTO toDTO(Usuario usuario);

    @Mapping(source = "empleadoId", target = "empleado.id_Empleado")
    Usuario toEntity(UsuarioDTO usuarioDTO);
}
