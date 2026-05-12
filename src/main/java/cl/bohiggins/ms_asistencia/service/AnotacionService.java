package cl.bohiggins.ms_asistencia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.bohiggins.ms_asistencia.dto.AnotacionCreateRequest;
import cl.bohiggins.ms_asistencia.entity.Anotacion;
import cl.bohiggins.ms_asistencia.entity.TipoAnotacion;
import cl.bohiggins.ms_asistencia.repository.AnotacionRepository;

@Service
public class AnotacionService {

	@Autowired
	private AnotacionRepository repositorio;

	public Anotacion guardarAnotacion(AnotacionCreateRequest req) {
		Anotacion a = new Anotacion();
		a.setCursoId(req.cursoId());
		a.setEstudianteId(req.estudianteId());
		a.setFecha(req.fecha());
		a.setTipo(req.tipo());
		a.setDescripcion(req.descripcion());
		a.setRegistradaPor(req.registradaPor());
		return repositorio.save(a);
	}

	public List<Anotacion> obtenerAnotaciones() {
		return repositorio.findAll();
	}

	public Anotacion obtenerAnotacionID(Long id) {
		return repositorio.findById(id).orElse(null);
	}

	public List<Anotacion> obtenerAnotacionesPorEstudiante(Long estudianteId) {
		return repositorio.findByEstudianteId(estudianteId);
	}

	public List<Anotacion> obtenerAnotacionesPorCurso(Long cursoId) {
		return repositorio.findByCursoId(cursoId);
	}

	public List<Anotacion> obtenerAnotacionesPorTipo(TipoAnotacion tipo) {
		return repositorio.findByTipo(tipo);
	}

	public Anotacion modificarAnotacion(Anotacion a_mod) {
		Anotacion a = repositorio.findById(a_mod.getId()).orElse(null);
		if (a == null) {
			return null;
		}
		a.setCursoId(a_mod.getCursoId());
		a.setEstudianteId(a_mod.getEstudianteId());
		a.setFecha(a_mod.getFecha());
		a.setTipo(a_mod.getTipo());
		a.setDescripcion(a_mod.getDescripcion());
		a.setRegistradaPor(a_mod.getRegistradaPor());
		return repositorio.save(a);
	}

	public String borrarAnotacion(Long id) {
		repositorio.deleteById(id);
		return "Anotacion eliminada correctamente.";
	}
}
