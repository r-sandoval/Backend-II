package com.petsalud.backendpetsalud.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ClinicaController {

    @GetMapping("/public/info")
    public ResponseEntity<String> infoPublica() {
        return ResponseEntity.ok("Bienvenido al recurso público de CLÍNICA PETSALUD");
    }

    @GetMapping("/cliente/mascotas")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<String> misMascotas() {
        return ResponseEntity.ok("Acceso autorizado: listado de mascotas del cliente");
    }

    @GetMapping("/empleado/citas")
    @PreAuthorize("hasAnyRole('EMPLEADO', 'GERENTE')")
    public ResponseEntity<String> citasDelDia() {
        return ResponseEntity.ok("Acceso autorizado: citas del día para empleados");
    }

    @GetMapping("/gerente/reportes")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<String> reportes() {
        return ResponseEntity.ok("Acceso autorizado: reportes de gestión para gerentes");
    }
}
