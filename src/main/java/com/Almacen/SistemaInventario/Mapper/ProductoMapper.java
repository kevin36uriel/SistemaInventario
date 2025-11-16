package com.Almacen.SistemaInventario.Mapper;

import com.Almacen.SistemaInventario.DTO.ProductoDTO;
import com.Almacen.SistemaInventario.Model.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductoMapper {
    @Mapping(source = "categoria.idCategoria", target = "idCategoria")
    ProductoDTO toDTO(Producto producto);

    @Mapping(source = "idCategoria", target = "categoria.idCategoria")
    @Mapping(target = "prestamos", ignore = true)
    Producto toEntity(ProductoDTO productoDTO);
}
