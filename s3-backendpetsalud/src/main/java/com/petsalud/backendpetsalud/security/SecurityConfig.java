package com.petsalud.backendpetsalud.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private UsuarioDetailsService usuarioDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF deshabilitado porque usamos JWT (stateless, sin cookies de sesion)
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Rutas publicas
                        .requestMatchers("/api/usuarios/registro", "/api/auth/login").permitAll()
                        // Rutas por rol (hasRole espera el prefijo ROLE_ que agrega UsuarioDetails)
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/veterinario/**").hasAnyRole("VETERINARIO", "ADMIN")
                        // Todo lo demas requiere autenticacion
                        .anyRequest().authenticated()
                )
                .userDetailsService(usuarioDetailsService)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // Registrar el JwtFilter antes del filtro de usuario/password
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}