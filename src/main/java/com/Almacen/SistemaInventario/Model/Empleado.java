package com.Almacen.SistemaInventario.Model;

import com.Almacen.SistemaInventario.Model.Enum.Area;
import com.Almacen.SistemaInventario.Model.Enum.Carrera;
import com.Almacen.SistemaInventario.Model.Enum.Rol;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Empleado")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Empleado")
    private Long id_Empleado;

    @Column(name = "Nombre", length = 100, nullable = false)
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Column(name = "Apellidos", length = 100, nullable = false)
    @NotBlank(message = "Los apellidos son obligatorios")
    private String apellidos;

    @Column(name = "Puesto", length = 25, nullable = false)
    @Enumerated(EnumType.STRING)
    private Rol rol;

    @Column(name = "Edificio")
    @Enumerated(EnumType.STRING)
    private Carrera carrera;

    @Column(name = "Area")
    @Enumerated(EnumType.STRING)
    private Area area;

    @Column(name = "Telefono", length = 20)
    @Pattern(regexp = "\\d{10}", message = "El teléfono debe tener 10 dígitos")
    private String telefono;

    @Column(name = "Email", unique = true)
    @Email(message = "Formato de correo inválido")
    private String email;

    @OneToOne(mappedBy = "empleado", cascade = CascadeType.ALL, orphanRemoval = true)
    private Usuario usuario;

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        if (usuario != null && usuario.getEmpleado() != this) {
            usuario.setEmpleado(this);
        }
    }
}