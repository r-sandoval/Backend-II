# Backend PETSALUD — Autenticación y Autorización con Spring Security + JWT

Backend de CLÍNICA PETSALUD con autenticación stateless mediante JSON Web Tokens (JWT) y autorización basada en roles (CLIENTE, EMPLEADO, GERENTE).

## Stack

- Spring Boot 3.3.6
- Java 21
- Spring Security 6
- JWT (jjwt 0.12.6)
- Spring Data JPA + H2 (en memoria)
- Lombok

## Cómo ejecutar

./mvnw clean compile
./mvnw spring-boot:run

La app arranca en http://localhost:8080.

Al iniciar, DataInitializer crea tres usuarios de prueba:

| Email | Password | Rol |
|---|---|---|
| cliente@petsalud.cl | 123456 | CLIENTE |
| empleado@petsalud.cl | 123456 | EMPLEADO |
| gerente@petsalud.cl | 123456 | GERENTE |

## Endpoints

| Método | Endpoint | Acceso |
|---|---|---|
| POST | /api/auth/register | Público |
| POST | /api/auth/login | Público |
| GET | /api/public/info | Público |
| GET | /api/cliente/mascotas | CLIENTE |
| GET | /api/empleado/citas | EMPLEADO o GERENTE |
| GET | /api/gerente/reportes | GERENTE |

## Flujo JWT

1. POST /api/auth/login con email y password devuelve un token.
2. En cada request protegido, enviar Authorization: Bearer TOKEN.
3. JwtAuthFilter valida firma y expiración, carga el usuario y registra la autenticación.
4. SecurityConfig y PreAuthorize controlan el acceso por rol.

## Pruebas de acceso

| Rol | Endpoint | Resultado |
|---|---|---|
| CLIENTE | /api/cliente/mascotas | 200 |
| CLIENTE | /api/gerente/reportes | 403 |
| EMPLEADO | /api/empleado/citas | 200 |
| EMPLEADO | /api/gerente/reportes | 403 |
| GERENTE | /api/gerente/reportes | 200 |
| Sin token | /api/public/info | 200 |

## Buenas prácticas aplicadas

- Contraseñas hasheadas con BCrypt.
- JWT firmado con HMAC-SHA (HS384 por tamaño de clave) y expiración de 1 hora.
- Payload minimalista (sin datos sensibles).
- CSRF deshabilitado por ser API stateless.
- Authorities con prefijo ROLE_ para uso de hasRole.
- Secreto JWT en application.properties (en producción debe ir en variable de entorno).
