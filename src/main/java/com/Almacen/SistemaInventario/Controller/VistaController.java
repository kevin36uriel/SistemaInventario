package com.Almacen.SistemaInventario.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/Login")
public class VistaController {
    @GetMapping("/User")
    public String login() {
        return "Inicio";
    }

    @GetMapping("/ISC")
    public String bienvenido() {
        return "ISC/isc";
    }
    
    @GetMapping("/IGE")
    public String bienvenidoige() {
        return "IGE/ige";
    }

    @GetMapping("/IA")
    public String bienvenidoia() {
        return "IA/ia";
    }
    
    @GetMapping("/II")
    public String bienvenidoii() {
        return "II/ii";
    }

    @GetMapping("/ALMACEN")
    public String almacen() {
        return "ALMACEN/almacen";
    }
}

