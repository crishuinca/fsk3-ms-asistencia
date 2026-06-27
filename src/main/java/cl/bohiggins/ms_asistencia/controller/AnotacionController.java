package cl.bohiggins.ms_asistencia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.bohiggins.ms_asistencia.dto.AnotacionCreateRequest;
import cl.bohiggins.ms_asistencia.entity.Anotacion;
import cl.bohiggins.ms_asistencia.entity.TipoAnotacion;
import cl.bohiggins.ms_asistencia.service.AnotacionService;
import cl.bohiggins.ms_asistencia.web.RecursoHttp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Anotaciones V1", description = "Anotaciones positivas y negativas a estudiantes")
public class AnotacionController {

	@Autowired
	private AnotacionService servicio;

	@Operation(summary = "Registrar anotación")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Anotación creada") })
	@PostMapping("/addAnotacion")
	@ResponseStatus(HttpStatus.CREATED)
	public Anotacion c_guardarAnotacion(@Valid @RequestBody AnotacionCreateRequest req) {
		return servicio.guardarAnotacion(req);
	}

	@Operation(summary = "Listar todas las anotaciones")
	@GetMapping("/anotaciones")
	public List<Anotacion> c_obtenerAnotaciones() {
		return servicio.obtenerAnotaciones();
	}

	@Operation(summary = "Obtener anotación por ID")
	@GetMapping("/anotacionByID/{id}")
	public Anotacion c_obtenerAnotacionID(@PathVariable Long id) {
		return RecursoHttp.requerir(servicio.obtenerAnotacionID(id), "Anotacion no encontrada.");
	}

	@Operation(summary = "Listar anotaciones de un estudiante")
	@GetMapping("/anotacionesPorEstudiante/{estudianteId}")
	public List<Anotacion> c_obtenerAnotacionesPorEstudiante(@PathVariable Long estudianteId) {
		return servicio.obtenerAnotacionesPorEstudiante(estudianteId);
	}

	@Operation(summary = "Listar anotaciones de un curso")
	@GetMapping("/anotacionesPorCurso/{cursoId}")
	public List<Anotacion> c_obtenerAnotacionesPorCurso(@PathVariable Long cursoId) {
		return servicio.obtenerAnotacionesPorCurso(cursoId);
	}

	@Operation(summary = "Listar anotaciones por tipo (POSITIVA o NEGATIVA)")
	@GetMapping("/anotacionesPorTipo/{tipo}")
	public List<Anotacion> c_obtenerAnotacionesPorTipo(@PathVariable TipoAnotacion tipo) {
		return servicio.obtenerAnotacionesPorTipo(tipo);
	}

	@Operation(summary = "Modificar anotación")
	@PutMapping("/modificarAnotacion")
	public Anotacion c_modificarAnotacion(@RequestBody Anotacion a) {
		return RecursoHttp.requerir(servicio.modificarAnotacion(a), "Anotacion no encontrada.");
	}

	@Operation(summary = "Eliminar anotación")
	@DeleteMapping("/eliminarAnotacion/{id}")
	public String c_borrarAnotacion(@PathVariable Long id) {
		return servicio.borrarAnotacion(id);
	}
}
