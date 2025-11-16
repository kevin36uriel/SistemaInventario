package com.Almacen.SistemaInventario.Service;

import com.Almacen.SistemaInventario.DTO.CategoriaDTO;
import com.Almacen.SistemaInventario.Mapper.CategoriaMapper;
import com.Almacen.SistemaInventario.Model.Categoria;
import com.Almacen.SistemaInventario.Repository.CategoriaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaMapper categoriaMapper;

    public List<CategoriaDTO> getCategoria(){
        return categoriaRepository.findAll().stream().map(categoriaMapper::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public CategoriaDTO saveCategoria(CategoriaDTO categoriaDTO){
        if(categoriaDTO.getNombreCategoria() == null){
            throw new IllegalArgumentException("El nombre de la Categoria no puede ser nulo");
        }

        Categoria categoria = categoriaMapper.toEntity(categoriaDTO);
        @SuppressWarnings("null")
        Categoria guardar = categoriaRepository.save(categoria);

        return categoriaMapper.toDTO(guardar);
    }

    @Transactional
    public Optional<CategoriaDTO> updateCategoria(CategoriaDTO categoriaDTO, String nombreCategoria) {
        return categoriaRepository.findByNombreCategoria(nombreCategoria).map(categoria -> {
            if(categoriaDTO.getNombreCategoria() != null){
                categoria.setNombreCategoria(categoriaDTO.getNombreCategoria());
            }

            @SuppressWarnings("null")
            Categoria categoriaEdita = categoriaRepository.save(categoria);
            return categoriaMapper.toDTO(categoriaEdita);
        });
    }

    @SuppressWarnings("null")
    @Transactional
    public boolean deleteCategoria(String nombreCategoria){
        Optional<Categoria> categoria = categoriaRepository.findByNombreCategoria(nombreCategoria);
        if(categoria.isPresent()){
            categoriaRepository.delete(categoria.get());
            return true;
        }
        return false;
    }

}
