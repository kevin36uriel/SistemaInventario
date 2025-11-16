package com.Almacen.SistemaInventario.Controller;

import com.Almacen.SistemaInventario.DTO.UsuarioDTO;
import com.Almacen.SistemaInventario.Service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/GetAllUsuario")
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios() {
        return new ResponseEntity<>(usuarioService.getUsuarios(), HttpStatus.OK);
    }

    @GetMapping("/GetUsuario")
    public ResponseEntity<UsuarioDTO> getOne(@RequestParam String usuario) {
        Optional<UsuarioDTO> usuarioExiste = usuarioService.getUsuarioPorUsername(usuario);
        return usuarioExiste.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/ActualizaUsuario")
    public ResponseEntity<UsuarioDTO> putUsuario(@RequestParam String usuario, @Valid @RequestBody UsuarioDTO usuarioDTO) {
        try {
            Optional<UsuarioDTO> actualizaUsuario = usuarioService.updateUsuario(usuario, usuarioDTO);
            return actualizaUsuario.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}