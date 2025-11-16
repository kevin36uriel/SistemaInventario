package com.Almacen.SistemaInventario.DTO;

import com.Almacen.SistemaInventario.Model.Empleado;
import com.Almacen.SistemaInventario.Model.Enum.Area;
import com.Almacen.SistemaInventario.Model.Enum.Carrera;
import com.Almacen.SistemaInventario.Model.Enum.Rol;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmpleadoDTO {
    private Long id_Empleado;
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(min = 2, max = 100, message = "Los apellidos deben tener entre 2 y 100 caracteres")
    private String apellidos;

    @NotNull
    private Rol rol;

    @NotNull
    private Carrera carrera;

    @NotNull
    private Area area;

    @Pattern(regexp = "\\d{10}", message = "El teléfono debe tener 10 dígitos")
    private String telefono;

    @Email(message = "Formato de correo inválido")
    private String email;

    private Long usuarioId;
    @Valid
    private UsuarioDTO usuario;

    public EmpleadoDTO(Empleado empleado) {
        this.id_Empleado = empleado.getId_Empleado();
        this.nombre = empleado.getNombre();
        this.apellidos = empleado.getApellidos();
        this.rol = empleado.getRol();
        this.carrera = empleado.getCarrera();
        this.area = empleado.getArea();
        this.telefono = empleado.getTelefono();
        this.email = empleado.getEmail();
        if (empleado.getUsuario() != null) {
            this.usuarioId = empleado.getUsuario().getId_Usuario();
            this.usuario = new UsuarioDTO(empleado.getUsuario());
        }
    }
}