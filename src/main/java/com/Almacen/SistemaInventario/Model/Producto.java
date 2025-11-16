package com.Almacen.SistemaInventario.Model;

import com.Almacen.SistemaInventario.Model.Enum.Area;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProducto")
    private Long idProducto;

    @Column(name = "nombre", length = 100)
    @NotBlank(message = "El nombre del producto no puede estar vacio")
    private String nombre;

    @Column(name = "descripcion", length = 250)
    @NotBlank(message = "La descripcion del producto no puede estar vacia")
    private String descripcion;

    @Column(name = "codigo", length = 50, unique = true)
    @NotBlank(message = "El codigo del producto no puede estar vacio")
    private String codigo;

    @ManyToOne
    @JoinColumn(name = "idCategoria")
    @NotNull(message = "La categoria no púede estar vacia")
    private Categoria categoria;

    @Enumerated(EnumType.STRING)
    private Area area;

    @Column(name = "cantidadDisponible")
    private int cantidadDisponible = 0;

    // === Relación bidireccional con Prestamo ===
    // Al eliminar un Producto desde JPA, se eliminarán sus Prestamos.
    @OneToMany(
            mappedBy = "producto",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @ToString.Exclude
    private List<Prestamo> prestamos = new ArrayList<>();

    // Métodos de conveniencia (opcional, pero útiles)
    public void addPrestamo(Prestamo prestamo) {
        prestamos.add(prestamo);
        prestamo.setProducto(this);
    }

    public void removePrestamo(Prestamo prestamo) {
        prestamos.remove(prestamo);
        prestamo.setProducto(null);
    }
}
