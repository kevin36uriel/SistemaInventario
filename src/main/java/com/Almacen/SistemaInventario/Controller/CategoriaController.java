package com.Almacen.SistemaInventario.Controller;

import com.Almacen.SistemaInventario.DTO.CategoriaDTO;
import com.Almacen.SistemaInventario.Model.Enum.Area;
import com.Almacen.SistemaInventario.Service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;


    @GetMapping("/GetAllCategoria")
    public ResponseEntity<List<CategoriaDTO>> getAllCategoria(){
        return new ResponseEntity<>(categoriaService.getCategoria(), HttpStatus.OK);
    }

    @GetMapping("/GetAllAreas")
    public ResponseEntity<List<String>> getAllAreas() {
        List<String> areas = Arrays.stream(Area.values())
                .map(Enum::name)
                .toList();
        return new ResponseEntity<>(areas, HttpStatus.OK);
    }

    @PostMapping("/SaveCategoria")
    public ResponseEntity<CategoriaDTO> saveCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO){
        try{
            CategoriaDTO guardarCategoria = categoriaService.saveCategoria(categoriaDTO);
            return new ResponseEntity<>(guardarCategoria,HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/UpdateCategoria")
    public ResponseEntity<CategoriaDTO> updateCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO, @RequestParam String nombreCategoria){
        try {
            Optional<CategoriaDTO> actualizarCategoria = categoriaService.updateCategoria(categoriaDTO, nombreCategoria);
            return actualizarCategoria.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK)).orElseGet(() -> ResponseEntity.notFound().build());
        }catch (IllegalArgumentException e){
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/DeleteCategoria")
    public ResponseEntity<Void> deleteCategoria(@RequestParam String nombreCategoria){
        boolean eliminado = categoriaService.deleteCategoria(nombreCategoria);
        if(eliminado){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
