package com.Almacen.SistemaInventario.DTO;


import com.Almacen.SistemaInventario.Model.Enum.Area;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductoDTO {

    private Long idProducto;

    @NotBlank(message = "El nombre del producto no puede estar vacio")
    private String nombre;

    @NotBlank(message = "La descripcion del producto no puede estar vacia")
    private String descripcion;

    @NotBlank(message = "El codigo del producto no puede estar vacio")
    private String codigo;

    @NotNull(message = "La categoria no púede estar vacia")
    private Long idCategoria;

    private Area area;


    private int cantidadDisponible = 0;

    public ProductoDTO(Long idProducto, String nombre, String descripcion, Long idCategoria, Area area,int cantidadDisponible, String codigo) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idCategoria = idCategoria;
        this.area = area;
        this.codigo = codigo;
        this.cantidadDisponible = cantidadDisponible;
    }
}
