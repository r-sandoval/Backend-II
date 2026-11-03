package com.petsalud.backendpetsalud.config;

import com.petsalud.backendpetsalud.entity.Rol;
import com.petsalud.backendpetsalud.entity.Usuario;
import com.petsalud.backendpetsalud.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initUsers(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            crearSiNoExiste(usuarioRepository, passwordEncoder, "cliente@petsalud.cl", "123456", Rol.CLIENTE, "Cliente");
            crearSiNoExiste(usuarioRepository, passwordEncoder, "empleado@petsalud.cl", "123456", Rol.EMPLEADO, "Empleado");
            crearSiNoExiste(usuarioRepository, passwordEncoder, "gerente@petsalud.cl", "123456", Rol.GERENTE, "Gerente");
        };
    }

    private void crearSiNoExiste(UsuarioRepository repo, PasswordEncoder encoder,
                                 String email, String password, Rol rol, String nombre) {
        if (repo.findByEmail(email).isEmpty()) {
            repo.save(Usuario.builder()
                    .email(email)
                    .password(encoder.encode(password))
                    .rol(rol)
                    .nombre(nombre)
                    .build());
            System.out.println("[DATA INIT] Usuario creado: " + email + " (" + rol + ")");
        }
    }
}
