package com.Almacen.SistemaInventario.DTO;

import com.Almacen.SistemaInventario.Model.Enum.Area;
import com.Almacen.SistemaInventario.Model.Enum.Carrera;
import com.Almacen.SistemaInventario.Model.Enum.Rol;
import com.Almacen.SistemaInventario.Model.Usuario;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioDTO {
    private Long id_Usuario;
    @NotBlank(message = "El usuario es obligatorio")
    @Size(min = 3, max = 20, message = "El usuario debe tener entre 3 y 20 caracteres")
    private String usuario;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, max = 30, message = "La contraseña debe tener entre 6 y 30 caracteres")
    private String password;

    @NotNull(message = "El rol es obligatorio")
    private Rol rol;

    @NotNull(message = "El edificio es obligatorio")
    private Carrera carrera;

    @NotNull(message = "El area es obligatoria")
    private Area area;

    private Long empleadoId;

    public UsuarioDTO(Usuario usuario) {
        this.id_Usuario = usuario.getId_Usuario();
        this.usuario = usuario.getUsuario();
        this.password = usuario.getPassword();
        this.rol = usuario.getRol();
        this.carrera = usuario.getCarrera();
        this.area = usuario.getArea();
        if (usuario.getEmpleado() != null) {
            this.empleadoId = usuario.getEmpleado().getId_Empleado();
        }
    }
}