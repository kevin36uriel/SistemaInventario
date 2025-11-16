package com.Almacen.SistemaInventario.DTO;

import com.Almacen.SistemaInventario.Model.Enum.Estado;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PrestamoDTO {

    private Long idPrestamo;
    private Long producto;
    private String alumno;
    private String noControl;
    private Long usuario;
    private int cantidad;

    @JsonFormat(pattern = "yyyy-MM-dd")   // <— Asegura 2025-10-04
    private LocalDate fechaPrestamo;

    @JsonFormat(pattern = "yyyy-MM-dd")   // <— Asegura 2025-10-04
    private LocalDate fechaDevolucion;

    private Estado estado;

    public PrestamoDTO(Long idPrestamo, Long producto, String alumno, String noControl, Long usuario,int cantidad, LocalDate fechaPrestamo, LocalDate fechaDevolucion, Estado estado) {
        this.idPrestamo = idPrestamo;
        this.producto = producto;
        this.noControl = noControl;
        this.usuario = usuario;
        this.cantidad = cantidad;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.estado = estado;
    }
}
