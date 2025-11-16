package com.Almacen.SistemaInventario.Model;

import com.Almacen.SistemaInventario.Model.Enum.Area;
import com.Almacen.SistemaInventario.Model.Enum.Carrera;
import com.Almacen.SistemaInventario.Model.Enum.Rol;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Usuario")
    private Long id_Usuario;

    @Column(name = "Usuario", length = 20, unique = true, nullable = false)
    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 4, max = 20, message = "El usuario debe tener entre 4 y 20 caracteres")
    private String usuario;

    @Column(name = "Password", length = 60, nullable = false) // bcrypt usa 60
    @NotBlank(message = "La contraseña es obligatoria")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "Rol")
    @Enumerated(EnumType.STRING)
    private Rol rol;

    @Column(name = "Edificio")
    @Enumerated(EnumType.STRING)
    private Carrera carrera;

    @Column(name = "Area")
    @Enumerated(EnumType.STRING)
    private Area area;

    @OneToOne
    @JoinColumn(name = "id_Empleado", nullable = false)
    private Empleado empleado;

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
        if (empleado != null && empleado.getUsuario() != this) {
            empleado.setUsuario(this);
        }
    }
}