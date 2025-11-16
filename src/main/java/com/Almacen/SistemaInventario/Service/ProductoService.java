package com.Almacen.SistemaInventario.Service;

import com.Almacen.SistemaInventario.DTO.ProductoDTO;
import com.Almacen.SistemaInventario.Mapper.ProductoMapper;
import com.Almacen.SistemaInventario.Model.Categoria;
import com.Almacen.SistemaInventario.Model.Producto;
import com.Almacen.SistemaInventario.Repository.CategoriaRepository;
import com.Almacen.SistemaInventario.Repository.ProductoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

import java.util.List;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoMapper productoMapper;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<ProductoDTO> getProducto(){
        return productoRepository.findAll().stream().map(productoMapper::toDTO).toList();
    }

    @Transactional
    public ProductoDTO saveProducto(ProductoDTO productoDTO){

        if(productoDTO == null){
            throw new  NullPointerException("Ningun Campo puede estar vacio");
        }
        Producto guardar = productoMapper.toEntity(productoDTO);

        @SuppressWarnings("null")
        Producto guardado = productoRepository.save(guardar);

        return  productoMapper.toDTO(guardado);
    }

@SuppressWarnings("null")
@Transactional
    public ProductoDTO updateProducto(ProductoDTO productoDTO, Long id) {
        return productoRepository.findById(id).map(producto -> {

            if (productoDTO.getNombre() != null) {
                producto.setNombre(productoDTO.getNombre());
            }
            if (productoDTO.getDescripcion() != null) {
                producto.setDescripcion(productoDTO.getDescripcion());
            }
            if (productoDTO.getCodigo() != null) {
                producto.setCodigo(productoDTO.getCodigo());
            }
            if (productoDTO.getIdCategoria() != null) {
                @SuppressWarnings("null")
                Categoria categoria = categoriaRepository.findById(productoDTO.getIdCategoria())
                        .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));
                producto.setCategoria(categoria);
            }
            if (productoDTO.getArea() != null) {
                producto.setArea(productoDTO.getArea());
            }

            producto.setCantidadDisponible(productoDTO.getCantidadDisponible());

            Producto productoActualizado = productoRepository.save(producto);

            return productoMapper.toDTO(productoActualizado);

        }).orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
    }

    @SuppressWarnings("null")
    @Transactional
    public boolean deleteProducto(Long id){
        return productoRepository.findById(id).map(producto -> {
            productoRepository.delete(producto);
            return true;
        }).orElse(false);
    }

    @SuppressWarnings("null")
    public Optional<ProductoDTO> getById(Long id){
        return productoRepository.findById(id).map(productoMapper::toDTO);
    }
}
