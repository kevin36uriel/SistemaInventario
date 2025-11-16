package com.Almacen.SistemaInventario.Model;

import com.Almacen.SistemaInventario.Model.Enum.Estado;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "prestamo")
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPrestamo")
    private Long idPrestamo;

    // Si se borra el Producto en DB, Hibernate pedirá ON DELETE CASCADE
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idProducto", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Producto producto;

    @Column(name = "Alumno")
    private String alumno;

    @Column(name = "NoControl")
    private String noControl;

    // Opcional: también en cascada si borras un Usuario (si tu negocio lo permite)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_Usuario")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Usuario usuario;

    @Column(name = "cantidad")
    private int cantidad;

    @Column(name = "fechaPrestamo")
    private LocalDate fechaPrestamo;

    @Column(name = "fechaDevolucion")
    private LocalDate fechaDevolucion;

    @Column(name = "estado")
    @Enumerated(EnumType.STRING)
    private Estado estado;
}
