# Backend II - Semana 3: Integración de Seguridad

Este repositorio contiene las actividades de Desarrollo Backend II. 
La entrega actual corresponde a la **Semana 3 (S3)**, ubicada en la carpeta `s3-backendpetsalud`.

## S3: Integración completa de seguridad

Esta versión del backend implementa:
- Autenticación con **JWT** (firma HS256) y contraseñas con **BCrypt**.
- Autorización basada en roles (`ADMIN`, `VETERINARIO`, `CLIENTE`) usando `@PreAuthorize`.
- Configuración **Stateless** ideal para APIs RESTful.
- Pruebas de seguridad contra inyección SQL, manipulación de tokens y acceso no autorizado.

### Tecnologías
- Java 17, Spring Boot 3.3.6, Spring Security 6, JWT (jjwt 0.12.6), MySQL 8, Maven.

### Cómo ejecutar
1. Clonar el repositorio: `git clone https://github.com/r-sandoval/Backend-II.git`
2. Entrar a la carpeta: `cd s3-backendpetsalud`
3. Levantar MySQL (Docker): 
   `docker run --name mysql-petsalud -e MYSQL_ROOT_PASSWORD=root123 -e MYSQL_DATABASE=letrasypapeles_db -p 3308:3306 -d mysql:8`
4. Compilar y ejecutar:
   `mvn spring-boot:run`

### Endpoints principales
- `POST /api/auth/login`: Autenticación y generación de JWT.
- `POST /api/usuarios/registro`: Registro de usuarios.
- `GET /api/productos`: Listar productos (requiere token).
- `POST /api/productos`: Crear producto (solo ADMIN).

---
*Semana 2 (S2) también se encuentra en este repositorio para referencia.*
