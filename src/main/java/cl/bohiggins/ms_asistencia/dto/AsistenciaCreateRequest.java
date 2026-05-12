package cl.bohiggins.ms_asistencia.dto;

import java.time.LocalDate;

import cl.bohiggins.ms_asistencia.entity.EstadoAsistencia;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Datos para registrar la asistencia diaria de un estudiante")
public record AsistenciaCreateRequest(
		@NotNull @Schema(description = "ID del curso", example = "1") Long cursoId,
		@NotNull @Schema(description = "ID del estudiante", example = "1") Long estudianteId,
		@NotNull @Schema(example = "2026-05-08") LocalDate fecha,
		@NotNull @Schema(description = "PRESENTE | AUSENTE | ATRASADO | JUSTIFICADO", example = "PRESENTE") EstadoAsistencia estado,
		@Schema(description = "Observación opcional", example = "Llegó 10 minutos tarde") String observacion,
		@NotBlank @Schema(description = "RUT del profesor que registra", example = "12345678-9") String registradaPor
) {
}
