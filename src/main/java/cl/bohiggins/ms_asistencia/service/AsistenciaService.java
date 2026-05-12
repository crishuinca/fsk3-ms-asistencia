package cl.bohiggins.ms_asistencia.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.bohiggins.ms_asistencia.dto.AsistenciaCreateRequest;
import cl.bohiggins.ms_asistencia.entity.Asistencia;
import cl.bohiggins.ms_asistencia.repository.AsistenciaRepository;

@Service
public class AsistenciaService {

	@Autowired
	private AsistenciaRepository repositorio;

	public Asistencia guardarAsistencia(AsistenciaCreateRequest req) {
		repositorio.findByEstudianteIdAndFecha(req.estudianteId(), req.fecha())
				.ifPresent(a -> {
					throw new IllegalArgumentException("Ya existe asistencia del estudiante para esa fecha.");
				});
		Asistencia a = new Asistencia();
		a.setCursoId(req.cursoId());
		a.setEstudianteId(req.estudianteId());
		a.setFecha(req.fecha());
		a.setEstado(req.estado());
		a.setObservacion(req.observacion());
		a.setRegistradaPor(req.registradaPor());
		return repositorio.save(a);
	}

	public List<Asistencia> obtenerAsistencias() {
		return repositorio.findAll();
	}

	public Asistencia obtenerAsistenciaID(Long id) {
		return repositorio.findById(id).orElse(null);
	}

	public List<Asistencia> obtenerAsistenciasPorCursoYFecha(Long cursoId, LocalDate fecha) {
		return repositorio.findByCursoIdAndFecha(cursoId, fecha);
	}

	public List<Asistencia> obtenerAsistenciasPorEstudiante(Long estudianteId) {
		return repositorio.findByEstudianteId(estudianteId);
	}

	public Asistencia modificarAsistencia(Asistencia a_mod) {
		Asistencia a = repositorio.findById(a_mod.getId()).orElse(null);
		if (a == null) {
			return null;
		}
		a.setCursoId(a_mod.getCursoId());
		a.setEstudianteId(a_mod.getEstudianteId());
		a.setFecha(a_mod.getFecha());
		a.setEstado(a_mod.getEstado());
		a.setObservacion(a_mod.getObservacion());
		a.setRegistradaPor(a_mod.getRegistradaPor());
		return repositorio.save(a);
	}

	public String borrarAsistencia(Long id) {
		repositorio.deleteById(id);
		return "Asistencia eliminada correctamente.";
	}
}
