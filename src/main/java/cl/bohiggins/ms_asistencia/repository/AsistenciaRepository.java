package cl.bohiggins.ms_asistencia.repository;

import cl.bohiggins.ms_asistencia.entity.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {

	List<Asistencia> findByCursoIdAndFecha(Long cursoId, LocalDate fecha);

	List<Asistencia> findByEstudianteId(Long estudianteId);

	Optional<Asistencia> findByEstudianteIdAndFecha(Long estudianteId, LocalDate fecha);
}
