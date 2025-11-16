package com.Almacen.SistemaInventario.DTO;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoriaDTO {

    private Long idCategoria;

    @NotBlank(message = "El nombre de la categoria no puede estar vacio")
    private String nombreCategoria;

    public CategoriaDTO(Long idCategoria, String nombreCategoria) {
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
    }
}
