# ms-asistencia

Microservicio de asistencia y anotaciones de la Plataforma de Libro de Clases Digital del Colegio Bernardo O'Higgins.

Este servicio administra la asistencia diaria de los estudiantes y sus anotaciones positivas o negativas. Guarda referencias lógicas a estudiantes y cursos mediante IDs, y el BFF se encarga de cruzar esos datos con `ms-academico`.

## Tecnologías

- Java 17
- Spring Boot 3.5.7
- Maven
- Spring Web
- Spring Data JPA
- H2 en modo archivo
- Bean Validation
- Swagger / OpenAPI con springdoc 2.8.6
- JUnit 5, Mockito y JaCoCo

## Puerto y URLs

El servicio corre en el puerto `8082`.

- API base: `http://localhost:8082/api/v1`
- Swagger UI: `http://localhost:8082/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8082/v3/api-docs`
- H2 console: `http://localhost:8082/h2-console`

Datos para H2:

- JDBC URL: `jdbc:h2:file:./data/asistenciadb`
- Usuario: `sa`
- Password: vacío

## Cómo ejecutar

Desde la carpeta del proyecto:

```powershell
cd "C:\Users\tobal\Desktop\Fullstack 3\ms-asistencia"
.\mvnw.cmd spring-boot:run
```

Para compilar:

```powershell
.\mvnw.cmd clean compile
```

## Tests y cobertura

Ejecutar tests con reporte de cobertura:

```powershell
.\mvnw.cmd verify
```

El reporte HTML de JaCoCo queda en:

```text
target/site/jacoco/index.html
```

Estado actual:

- 21 tests.
- Cobertura global aproximada: 91% por líneas.
- Regla JaCoCo: mínimo 60% para la capa `service`.

## CI/CD y SonarQube

El repositorio incluye un pipeline de GitHub Actions en:

```text
.github/workflows/ci-sonar.yml
```

El pipeline ejecuta:

- Java 17.
- `.\mvnw.cmd verify` equivalente en Linux: `./mvnw -B verify`.
- Reporte JaCoCo como artefacto.
- Análisis SonarQube/SonarCloud si existen las variables y secretos necesarios.

Para activar Sonar en GitHub se debe configurar:

- Secret: `SONAR_TOKEN`
- Variable: `SONAR_ORGANIZATION`
- Variable opcional: `SONAR_PROJECT_KEY`
- Variable opcional: `SONAR_HOST_URL` si se usa SonarQube propio en vez de SonarCloud.

## Endpoints principales

Asistencias:

- `POST /api/v1/addAsistencia`
- `GET /api/v1/asistencias`
- `GET /api/v1/asistenciaByID/{id}`
- `GET /api/v1/asistenciasPorCursoYFecha?cursoId=1&fecha=2026-05-08`
- `GET /api/v1/asistenciasPorEstudiante/{estudianteId}`
- `PUT /api/v1/modificarAsistencia`
- `DELETE /api/v1/eliminarAsistencia/{id}`

Anotaciones:

- `POST /api/v1/addAnotacion`
- `GET /api/v1/anotaciones`
- `GET /api/v1/anotacionByID/{id}`
- `GET /api/v1/anotacionesPorEstudiante/{estudianteId}`
- `GET /api/v1/anotacionesPorCurso/{cursoId}`
- `GET /api/v1/anotacionesPorTipo/{tipo}`
- `PUT /api/v1/modificarAnotacion`
- `DELETE /api/v1/eliminarAnotacion/{id}`

## Reglas de negocio

- Una asistencia por estudiante y fecha.
- Una anotación puede ser `POSITIVA` o `NEGATIVA`.
- Una asistencia puede ser `PRESENTE`, `AUSENTE`, `ATRASADO` o `JUSTIFICADO`.
- No se usan relaciones JPA con estudiantes o cursos, solo `estudianteId` y `cursoId`.
- La carpeta `data/` no se debe subir a GitHub.

## Rol dentro de la arquitectura

`ms-asistencia` no es consumido directamente por el frontend. El flujo esperado es:

```text
Frontend React -> BFF -> ms-asistencia
```

El BFF llama a este microservicio para obtener o registrar asistencias y anotaciones. Cuando se registra una asistencia o anotación desde el frontend, el BFF obtiene primero el curso del estudiante desde `ms-academico` y luego envía el `cursoId` a este servicio.
