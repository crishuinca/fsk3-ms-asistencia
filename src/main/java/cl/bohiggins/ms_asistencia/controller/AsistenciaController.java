package cl.bohiggins.ms_asistencia.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.bohiggins.ms_asistencia.dto.AsistenciaCreateRequest;
import cl.bohiggins.ms_asistencia.entity.Asistencia;
import cl.bohiggins.ms_asistencia.service.AsistenciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Asistencias V1", description = "Asistencia diaria de estudiantes")
public class AsistenciaController {

	@Autowired
	private AsistenciaService servicio;

	@Operation(summary = "Registrar asistencia", description = "Una sola asistencia por estudiante por fecha")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Asistencia creada") })
	@PostMapping("/addAsistencia")
	public Asistencia c_guardarAsistencia(@Valid @RequestBody AsistenciaCreateRequest req) {
		return servicio.guardarAsistencia(req);
	}

	@Operation(summary = "Listar todas las asistencias")
	@GetMapping("/asistencias")
	public List<Asistencia> c_obtenerAsistencias() {
		return servicio.obtenerAsistencias();
	}

	@Operation(summary = "Obtener asistencia por ID")
	@GetMapping("/asistenciaByID/{id}")
	public Asistencia c_obtenerAsistenciaID(@PathVariable Long id) {
		return servicio.obtenerAsistenciaID(id);
	}

	@Operation(summary = "Listar asistencias de un curso en una fecha")
	@GetMapping("/asistenciasPorCursoYFecha")
	public List<Asistencia> c_obtenerAsistenciasPorCursoYFecha(
			@RequestParam Long cursoId,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
		return servicio.obtenerAsistenciasPorCursoYFecha(cursoId, fecha);
	}

	@Operation(summary = "Listar asistencias de un estudiante")
	@GetMapping("/asistenciasPorEstudiante/{estudianteId}")
	public List<Asistencia> c_obtenerAsistenciasPorEstudiante(@PathVariable Long estudianteId) {
		return servicio.obtenerAsistenciasPorEstudiante(estudianteId);
	}

	@Operation(summary = "Modificar asistencia")
	@PutMapping("/modificarAsistencia")
	public Asistencia c_modificarAsistencia(@RequestBody Asistencia a) {
		return servicio.modificarAsistencia(a);
	}

	@Operation(summary = "Eliminar asistencia")
	@DeleteMapping("/eliminarAsistencia/{id}")
	public String c_borrarAsistencia(@PathVariable Long id) {
		return servicio.borrarAsistencia(id);
	}
}
