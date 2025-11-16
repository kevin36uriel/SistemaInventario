package com.Almacen.SistemaInventario.Controller;

import com.Almacen.SistemaInventario.DTO.PrestamoDTO;

import com.Almacen.SistemaInventario.Service.PrestamoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Prestamo")
public class PrestamoController {
    @Autowired
    private PrestamoService prestamoService;

    @GetMapping("/GetAllPrestamo")
    public ResponseEntity<List<PrestamoDTO>> getAllPrestamo(){
        return new ResponseEntity<>(prestamoService.getAllPrestamo(), HttpStatus.OK);
    }

    @PostMapping("/SavePrestamo")
    public ResponseEntity<PrestamoDTO> savePrestamo(@Valid @RequestBody PrestamoDTO prestamoDTO){
        try{
            PrestamoDTO savePrestamo = prestamoService.savePrestamo(prestamoDTO);

            return new ResponseEntity<>(savePrestamo,HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/UpdatePrestamo")
    public ResponseEntity<PrestamoDTO> updatePrestamo(@RequestParam Long id,@RequestBody PrestamoDTO dto) {
        PrestamoDTO actualizado = prestamoService.updatePrestamo(id, dto);
        return ResponseEntity.ok(actualizado);
    }


}
