package com.Almacen.SistemaInventario.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCategoria")
    private Long idCategoria;

    @Column(name = "nombreCategoria",length = 50)
    @NotBlank(message = "El nombre de la categoria no puede estar vacio")
    private String nombreCategoria;

}
