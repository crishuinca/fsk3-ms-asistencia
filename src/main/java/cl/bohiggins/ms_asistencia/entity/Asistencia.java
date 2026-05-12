package cl.bohiggins.ms_asistencia.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(
		name = "asistencias",
		uniqueConstraints = @UniqueConstraint(name = "uk_asistencia_estudiante_fecha", columnNames = { "estudiante_id", "fecha" })
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Asistencia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private LocalDate fecha;

	@Column(name = "curso_id", nullable = false)
	private Long cursoId;

	@Column(name = "estudiante_id", nullable = false)
	private Long estudianteId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 16)
	private EstadoAsistencia estado;

	@Column(length = 500)
	private String observacion;

	@Column(name = "registrada_por", nullable = false, length = 12)
	private String registradaPor;
}
