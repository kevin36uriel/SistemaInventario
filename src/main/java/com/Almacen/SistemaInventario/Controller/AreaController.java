package com.Almacen.SistemaInventario.Controller;

import com.Almacen.SistemaInventario.Model.Enum.Area;
import com.Almacen.SistemaInventario.Model.Enum.Carrera;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Areas")
public class AreaController {

    private static final Logger logger = LoggerFactory.getLogger(AreaController.class);

    @GetMapping("/GetUserAreas")
    public List<String> getAreasPorUsuario(@RequestParam String carrera) {
        try {
            Carrera carreraUsuario = Carrera.valueOf(carrera.toUpperCase());
            List<String> areas = Arrays.stream(Area.values())
                                        .filter(a -> a.getCarrera() == carreraUsuario)
                                        .map(Area::name)
                                        .collect(Collectors.toList());
            return areas;
        } catch (IllegalArgumentException e) {
            logger.warn("Carrera no válida: {}", carrera);
            return Collections.emptyList();
        }
    }

    @GetMapping("/GetAreasByCarrera")
    public List<String> getAreasByCarrera(@RequestParam String carrera) {
        try {
            Carrera carreraEnum = Carrera.valueOf(carrera);
            return Arrays.stream(Area.values())
                    .filter(area -> area.getCarrera() == carreraEnum)
                    .map(Enum::name)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Carrera no válida: " + carrera);
        }
    }
}
