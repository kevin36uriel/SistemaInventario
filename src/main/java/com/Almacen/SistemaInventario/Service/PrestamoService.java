package com.Almacen.SistemaInventario.Service;

import com.Almacen.SistemaInventario.DTO.PrestamoDTO;
import com.Almacen.SistemaInventario.Mapper.PrestamoMapper;
import com.Almacen.SistemaInventario.Model.Prestamo;
import com.Almacen.SistemaInventario.Model.Producto;
import com.Almacen.SistemaInventario.Repository.PrestamoRepository;
import com.Almacen.SistemaInventario.Repository.ProductoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private PrestamoMapper prestamoMapper;

    @Autowired
    private ProductoRepository productoRepository;

    public List<PrestamoDTO> getAllPrestamo(){
        return prestamoRepository.findAll().stream()
                .map(prestamoMapper::toDTO)
                .toList();
    }

    @Transactional
    public PrestamoDTO savePrestamo(PrestamoDTO prestamoDTO){
        if (prestamoDTO == null) {
            throw new NullPointerException("No dejes ningún valor vacío");
        }
        // Validaciones rápidas
        if (prestamoDTO.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que 0");
        }

        @SuppressWarnings("null")
        Producto producto = productoRepository.findById(prestamoDTO.getProducto())
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        if (producto.getCantidadDisponible() < prestamoDTO.getCantidad()) {
            throw new IllegalStateException("No hay producto suficiente disponible");
        }

        producto.setCantidadDisponible(producto.getCantidadDisponible() - prestamoDTO.getCantidad());
        productoRepository.save(producto);

        Prestamo nuevoPrestamo = prestamoMapper.toEntity(prestamoDTO);
        @SuppressWarnings("null")
        Prestamo guardado = prestamoRepository.save(nuevoPrestamo);
        return prestamoMapper.toDTO(guardado);
    }

@Transactional
public PrestamoDTO updatePrestamo(Long idPrestamo, PrestamoDTO dto) {
    @SuppressWarnings("null")
    Prestamo prestamo = prestamoRepository.findById(idPrestamo)
        .orElseThrow(() -> new IllegalArgumentException("Préstamo no encontrado"));

    // 👉 Si tu regla de negocio es “al editar solo se marca devuelto y fecha”
    //    entonces deshabilita cambios de producto/cantidad aquí:
    if (dto.getEstado() != null) {
        // Si pasa a DEVUELTO y antes no lo estaba, regresa stock 1 sola vez
        boolean antesDevuelto = prestamo.getEstado() != null &&
                                prestamo.getEstado().name().equalsIgnoreCase("DEVUELTO");
        boolean ahoraDevuelto  = dto.getEstado().name().equalsIgnoreCase("DEVUELTO");

        prestamo.setEstado(dto.getEstado());

        if (ahoraDevuelto && !antesDevuelto) {
            Producto producto = prestamo.getProducto(); // mismo producto del préstamo
            producto.setCantidadDisponible(producto.getCantidadDisponible() + prestamo.getCantidad());
            productoRepository.save(producto);

            // Si no llega fechaDevolucion, pon hoy
            if (dto.getFechaDevolucion() == null) {
                prestamo.setFechaDevolucion(java.time.LocalDate.now());
            }
        }
    }

    // Permite solo estos campos al editar:
    if (dto.getFechaDevolucion() != null) {
        prestamo.setFechaDevolucion(dto.getFechaDevolucion());
    }

    // (Opcional) si también quieres permitir corregir alumno/noControl:
    if (dto.getAlumno() != null)    prestamo.setAlumno(dto.getAlumno());
    if (dto.getNoControl() != null) prestamo.setNoControl(dto.getNoControl());

    // ⚠️ NO cambies producto ni cantidad en update si tu flujo no lo requiere
    // para evitar desbalance de stock accidental.

    @SuppressWarnings("null")
    Prestamo guardado = prestamoRepository.save(prestamo);
    return prestamoMapper.toDTO(guardado);
}

}
