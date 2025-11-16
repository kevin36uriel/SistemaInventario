package com.Almacen.SistemaInventario.Service;

import com.Almacen.SistemaInventario.Model.Usuario;
import com.Almacen.SistemaInventario.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Optional<Usuario> login(String username, String password) {
        return usuarioRepository.findByUsuarioAndPassword(username, password.trim());
    }
}
