package com.Almacen.SistemaInventario.Controller;
import com.Almacen.SistemaInventario.Model.Usuario;
import com.Almacen.SistemaInventario.Service.LoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/Tecnologico")
public class LoginController {
    @Autowired
    private LoginService loginService;


@PostMapping("/Autenticar")
public Map<String, Object> autenticar(@RequestParam String usuario, @RequestParam String password) {
    Map<String, Object> response = new HashMap<>();
    Optional<Usuario> usuarioExiste = loginService.login(usuario, password);

    if (usuarioExiste.isPresent()) {
        Usuario u = usuarioExiste.get();
        response.put("success", true);
        response.put("edificio", u.getCarrera().toString());
        response.put("idUsuario", u.getId_Usuario());
        response.put("rol", u.getRol().toString());

        if (u.getEmpleado() != null) {
            response.put("idEmpleado", u.getEmpleado().getId_Empleado());
            response.put("nombre", u.getEmpleado().getNombre());
            response.put("apellido", u.getEmpleado().getApellidos());
        } else {
            response.put("idEmpleado", null);
            response.put("nombre", u.getUsuario());
            response.put("apellido", "");
        }
    } else {
        response.put("success", false);
        response.put("message", "Usuario o contraseña incorrectos");
    }
    return response;
}
}