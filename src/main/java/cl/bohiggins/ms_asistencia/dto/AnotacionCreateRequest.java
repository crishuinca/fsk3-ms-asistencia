package cl.bohiggins.ms_asistencia.dto;

import java.time.LocalDate;

import cl.bohiggins.ms_asistencia.entity.TipoAnotacion;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Datos para registrar una anotación a un estudiante")
public record AnotacionCreateRequest(
		@NotNull @Schema(description = "ID del curso", example = "1") Long cursoId,
		@NotNull @Schema(description = "ID del estudiante", example = "1") Long estudianteId,
		@NotNull @Schema(example = "2026-05-08") LocalDate fecha,
		@NotNull @Schema(description = "POSITIVA | NEGATIVA", example = "POSITIVA") TipoAnotacion tipo,
		@NotBlank @Schema(example = "Participó activamente en clase de matemáticas") String descripcion,
		@NotBlank @Schema(description = "RUT del profesor que registra", example = "12345678-9") String registradaPor
) {
}
