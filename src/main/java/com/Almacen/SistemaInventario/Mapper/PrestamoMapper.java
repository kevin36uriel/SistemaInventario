package com.Almacen.SistemaInventario.Mapper;

import com.Almacen.SistemaInventario.DTO.PrestamoDTO;
import com.Almacen.SistemaInventario.Model.Prestamo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PrestamoMapper {

    @Mapping(source = "producto.idProducto", target = "producto")
    @Mapping(source = "usuario.id_Usuario", target = "usuario")
    PrestamoDTO toDTO(Prestamo prestamo);

    @Mapping(source = "producto", target = "producto.idProducto")
    @Mapping(source = "usuario", target = "usuario.id_Usuario")
    Prestamo toEntity(PrestamoDTO prestamoDTO);
}
