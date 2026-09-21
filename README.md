# Franchise API

REST API para gestionar franquicias, sucursales y productos. Construida con Spring Boot 3.3.0 usando programación reactiva (WebFlux + R2DBC) y MySQL 8.0.

## Requisitos

- Java 17 o superior
- Maven 3.8+
- Docker y Docker Compose

## Inicio rápido

```bash
docker-compose up -d
```

Esto levanta MySQL y la aplicación. La API estará disponible en `http://localhost:8080`.

---

## Endpoints

### Franquicias

**Crear franquicia**
```
POST /api/franchises
Content-Type: application/json

{"name":"McDonald's"}
```

**Actualizar nombre**
```
PUT /api/franchises/{id}?name=New Name
```

**Obtener todas**
```
GET /api/franchises
```

**Obtener por ID**
```
GET /api/franchises/{id}
```

**Eliminar**
```
DELETE /api/franchises/{id}
```

---

### Sucursales

**Crear sucursal en franquicia**
```
POST /api/franchises/{franchiseId}/branches
Content-Type: application/json

{"name":"Sucursal Centro"}
```

**Actualizar nombre**
```
PUT /api/franchises/{franchiseId}/branches/{id}?name=New Name
```

**Obtener sucursales de franquicia**
```
GET /api/franchises/{franchiseId}/branches
```

**Obtener sucursal por ID**
```
GET /api/franchises/{franchiseId}/branches/{id}
```

**Eliminar sucursal**
```
DELETE /api/franchises/{franchiseId}/branches/{id}
```

---

### Productos

**Crear producto en sucursal**
```
POST /api/branches/{branchId}/products
Content-Type: application/json

{"name":"Hamburguesa","stock":100}
```

**Actualizar nombre**
```
PUT /api/branches/{branchId}/products/{id}?name=New Name
```

**Actualizar stock**
```
PUT /api/branches/{branchId}/products/{id}/stock
Content-Type: application/json

{"stock":50}
```

**Obtener productos de sucursal**
```
GET /api/branches/{branchId}/products
```

**Obtener producto por ID**
```
GET /api/branches/{branchId}/products/{id}
```

**Obtener producto con mayor stock de franquicia**
```
GET /api/franchises/{franchiseId}/products/max-stock
```

**Eliminar producto**
```
DELETE /api/branches/{branchId}/products/{id}
```

---

## Ejemplo de flujo completo

```bash
# 1. Crear franquicia
curl -X POST http://localhost:8080/api/franchises \
  -H "Content-Type: application/json" \
  -d '{"name":"McDonald'\''s"}'
# Respuesta: {"id":1,"name":"McDonald's"}

# 2. Crear sucursal (usar ID 1 de franquicia)
curl -X POST http://localhost:8080/api/franchises/1/branches \
  -H "Content-Type: application/json" \
  -d '{"name":"Sucursal Centro"}'
# Respuesta: {"id":1,"franchiseId":1,"name":"Sucursal Centro"}

# 3. Crear producto (usar ID 1 de sucursal)
curl -X POST http://localhost:8080/api/branches/1/products \
  -H "Content-Type: application/json" \
  -d '{"name":"Hamburguesa","stock":100}'
# Respuesta: {"id":1,"branchId":1,"name":"Hamburguesa","stock":100}

# 4. Actualizar stock (usar ID 1 de producto)
curl -X PUT http://localhost:8080/api/branches/1/products/1/stock \
  -H "Content-Type: application/json" \
  -d '{"stock":50}'
# Respuesta: {"id":1,"branchId":1,"name":"Hamburguesa","stock":50}

# 5. Obtener producto con mayor stock
curl -X GET http://localhost:8080/api/franchises/1/products/max-stock
# Respuesta: {"id":1,"branchId":1,"name":"Hamburguesa","stock":50}
```

---

## Arquitectura

Clean Architecture con capas:

- **Controller**: Manejo de requests HTTP
- **Service**: Lógica de negocio reactiva
- **Repository**: Acceso a datos con R2DBC
- **Domain**: Modelos de entidad
- **DTO**: Objetos de transferencia de datos
- **Exception**: Manejo centralizado de errores

Estructura del proyecto:

```
src/main/java/com/accenture/franchise/
├── controller/          # REST endpoints
├── service/            # Lógica de negocio
├── repository/         # Acceso a datos (R2DBC)
├── domain/             # Modelos de entidad
├── dto/                # Request/Response DTOs
└── exception/          # Manejo de errores
```

---

## Testing

```bash
mvn test
```

Incluye 17 tests unitarios con Mockito para:

- BranchService (5 tests)
- FranchiseService (5 tests)
- ProductService (7 tests)

---

## Stack Tecnológico

- **Spring Boot**: 3.3.0
- **Spring WebFlux**: Programación reactiva
- **R2DBC**: Acceso a datos reactivo
- **MySQL**: 8.0
- **Lombok**: Reducción de boilerplate
- **Mockito**: Testing unitario
- **Docker**: Containerización

---

## Construir la aplicación

```bash
mvn clean package
```

---

## Ejecutar localmente (sin Docker)

```bash
mvn spring-boot:run
```

---

## Docker Compose

Levanta MySQL y la API:

```bash
docker-compose up -d
```

Detener:

```bash
docker-compose down
```

Ver logs:

```bash
docker-compose logs -f franchise-api
```

---

## Respuestas de error

### 404 Not Found

```json
{
  "code": "NOT_FOUND",
  "message": "Recurso no encontrado"
}
```

### 500 Internal Server Error

```json
{
  "code": "INTERNAL_SERVER_ERROR",
  "message": "Error interno del servidor"
}
```

---

## Autor

Accenture Backend Developer Technical Test
