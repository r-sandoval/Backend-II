package com.petsalud.backendpetsalud.controller;

import com.petsalud.backendpetsalud.dto.AuthResponse;
import com.petsalud.backendpetsalud.dto.LoginRequest;
import com.petsalud.backendpetsalud.entity.Usuario;
import com.petsalud.backendpetsalud.repository.UsuarioRepository;
import com.petsalud.backendpetsalud.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // 1. Autenticar con AuthenticationManager
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            // 2. Obtener usuario ANTES de generar el token
            Usuario usuario = usuarioRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            // 3. Generar token JWT con el rol
            String token = jwtUtil.generateToken(loginRequest.getEmail(), usuario.getRol().name());

            return ResponseEntity.ok(new AuthResponse(token, usuario.getRol().name()));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Credenciales invalidas");
        }
    }
}