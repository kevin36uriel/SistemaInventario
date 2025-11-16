package com.Almacen.SistemaInventario.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.Almacen.SistemaInventario.DTO.EmpleadoDTO;
import com.Almacen.SistemaInventario.DTO.ProductoDTO;
import com.Almacen.SistemaInventario.Model.Usuario;
import com.Almacen.SistemaInventario.Repository.UsuarioRepository;
import com.Almacen.SistemaInventario.Service.EmpleadoService;
import com.Almacen.SistemaInventario.Service.PdfService;
import com.Almacen.SistemaInventario.Service.ProductoService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.http.*;



@RestController
@RequestMapping("/Productos")
public class ProductoController {
    @Autowired
    private ProductoService productoService;
    @Autowired
    private PdfService pdfService;
    @Autowired
    private EmpleadoService empleadoService;
    @Autowired
    private UsuarioRepository usuarioRepository; // <-- asegúrate de tenerlo

    @GetMapping("/GetAllProductos")
    public ResponseEntity<List<ProductoDTO>> getAllProductos(){
        return new ResponseEntity<>(productoService.getProducto(),HttpStatus.OK);
    }
    
    @PostMapping("/SaveProducto")
    public ResponseEntity<ProductoDTO> saveProducto(@Valid @RequestBody ProductoDTO productoDTO){
        try{
            ProductoDTO guardarProducto = productoService.saveProducto(productoDTO);
            return new ResponseEntity<>(guardarProducto,HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/UpdateProducto")
    public ResponseEntity<ProductoDTO> updateProducto(@Valid @RequestBody ProductoDTO productoDTO, @RequestParam Long id){
        try {
            ProductoDTO actualizarProducto = productoService.updateProducto(productoDTO, id);
            return new ResponseEntity<>(actualizarProducto, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @SuppressWarnings("null")
    @DeleteMapping(value = "/DeleteProducto", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<?> deleteProducto(@RequestParam Long id,@RequestParam String motivo,@RequestParam(required = false) Long empleadoId,@RequestParam Long usuarioId) {
        try {
            // Producto
            ProductoDTO producto = productoService.getById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

            // Intentar resolver empleado
            Optional<EmpleadoDTO> empleadoOpt = Optional.empty();

            if (empleadoId != null) {
                empleadoOpt = empleadoService.getById(empleadoId);
            }

            if (empleadoOpt.isEmpty()) {
                // Fallback por usuarioId
                Usuario u = usuarioRepository.findById(usuarioId)
                        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

                if (u.getEmpleado() != null && u.getEmpleado().getId_Empleado() != null) {
                    empleadoOpt = empleadoService.getById(u.getEmpleado().getId_Empleado());
                }
            }

            // Si sigue vacío, NO abortes. Construye un "empleado" mínimo con el nombre de usuario.
            EmpleadoDTO empleadoParaPDF = empleadoOpt.orElseGet(() -> {
                EmpleadoDTO e = new EmpleadoDTO();
                e.setNombre("Usuario: " + usuarioRepository.findById(usuarioId)
                        .map(Usuario::getUsuario).orElse("desconocido"));
                e.setApellidos("");
                return e;
            });

            // Generar PDF
            byte[] pdfBytes = pdfService.generarBajaProductoPdf(producto, empleadoParaPDF, motivo);

            // Eliminar
            boolean eliminado = productoService.deleteProducto(id);
            if (!eliminado) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se pudo eliminar: producto no encontrado");
            }

            String safeNombre = (producto.getNombre() != null ? producto.getNombre().replaceAll("[^\\w\\-]+","_") : "producto");
            String fileName = "Baja_" + safeNombre + ".pdf";

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .contentType(MediaType.APPLICATION_PDF)
                    .contentLength(pdfBytes.length)
                    .body(pdfBytes);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno al eliminar el producto: " + e.getMessage());
        }
    }

}
