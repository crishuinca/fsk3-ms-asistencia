package cl.bohiggins.ms_asistencia.repository;

import cl.bohiggins.ms_asistencia.entity.Anotacion;
import cl.bohiggins.ms_asistencia.entity.TipoAnotacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnotacionRepository extends JpaRepository<Anotacion, Long> {

	List<Anotacion> findByEstudianteId(Long estudianteId);

	List<Anotacion> findByCursoId(Long cursoId);

	List<Anotacion> findByTipo(TipoAnotacion tipo);
}
