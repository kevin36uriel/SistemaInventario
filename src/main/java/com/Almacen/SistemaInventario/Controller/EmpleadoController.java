package com.Almacen.SistemaInventario.Controller;

import com.Almacen.SistemaInventario.DTO.EmpleadoDTO;
import com.Almacen.SistemaInventario.Service.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Empleado")
public class EmpleadoController {
    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping("/GetAllEmpleado")
    public ResponseEntity<List<EmpleadoDTO>> getAllEmpleados() {
        return new ResponseEntity<>(empleadoService.getEmpleado(), HttpStatus.OK);
    }

    @GetMapping("/GetEmpleado")
    public ResponseEntity<EmpleadoDTO> getOne(@RequestParam(required = false) String nombre, @RequestParam(required = false) String apellidos) {
        Optional<EmpleadoDTO> empleadoExiste = empleadoService.getEmpleadoEspecifico(nombre, apellidos);
        return empleadoExiste.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/Enviar")
    public ResponseEntity<EmpleadoDTO> postEmpleado(@Valid @RequestBody EmpleadoDTO empleadoDTO) {
        try {
            EmpleadoDTO guardarEmpleado = empleadoService.saveEmpleado(empleadoDTO);
            return new ResponseEntity<>(guardarEmpleado, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/ActualizaEmpleado")
    public ResponseEntity<EmpleadoDTO> putEmpleado(@Valid @RequestBody EmpleadoDTO empleadoDTO, @RequestParam String nombre, @RequestParam String apellidos) {
        try {
            Optional<EmpleadoDTO> actualizaEmpleado = empleadoService.updateEmpleado(nombre, apellidos, empleadoDTO);
            return actualizaEmpleado.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/BorrarEmpleado")
    public ResponseEntity<Void> deleteEmpleado(@RequestParam String nombre, @RequestParam String apellidos) {
        boolean existe = empleadoService.deleteEmpleado(nombre,apellidos);
        if(existe){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}