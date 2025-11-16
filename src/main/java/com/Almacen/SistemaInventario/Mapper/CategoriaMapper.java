package com.Almacen.SistemaInventario.Mapper;

import com.Almacen.SistemaInventario.DTO.CategoriaDTO;
import com.Almacen.SistemaInventario.Model.Categoria;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface CategoriaMapper {
    CategoriaDTO toDTO(Categoria categoria);
    Categoria toEntity(CategoriaDTO dto);
}

