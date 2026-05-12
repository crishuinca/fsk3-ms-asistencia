package cl.bohiggins.ms_asistencia.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.bohiggins.ms_asistencia.dto.AsistenciaCreateRequest;
import cl.bohiggins.ms_asistencia.entity.Asistencia;
import cl.bohiggins.ms_asistencia.entity.EstadoAsistencia;
import cl.bohiggins.ms_asistencia.service.AsistenciaService;

@ExtendWith(MockitoExtension.class)
class AsistenciaControllerTest {

	@Mock
	private AsistenciaService servicio;

	@InjectMocks
	private AsistenciaController controller;

	@Test
	void asistenciaController_delegaOperaciones() {
		LocalDate fecha = LocalDate.of(2026, 5, 8);
		Asistencia asistencia = new Asistencia();
		asistencia.setId(1L);
		AsistenciaCreateRequest request = new AsistenciaCreateRequest(
				1L,
				2L,
				fecha,
				EstadoAsistencia.PRESENTE,
				"Sin observacion",
				"12345678-9"
		);
		List<Asistencia> asistencias = List.of(asistencia);
		when(servicio.guardarAsistencia(request)).thenReturn(asistencia);
		when(servicio.obtenerAsistencias()).thenReturn(asistencias);
		when(servicio.obtenerAsistenciaID(1L)).thenReturn(asistencia);
		when(servicio.obtenerAsistenciasPorCursoYFecha(1L, fecha)).thenReturn(asistencias);
		when(servicio.obtenerAsistenciasPorEstudiante(2L)).thenReturn(asistencias);
		when(servicio.modificarAsistencia(asistencia)).thenReturn(asistencia);
		when(servicio.borrarAsistencia(1L)).thenReturn("Asistencia eliminada correctamente.");

		assertSame(asistencia, controller.c_guardarAsistencia(request));
		assertSame(asistencias, controller.c_obtenerAsistencias());
		assertSame(asistencia, controller.c_obtenerAsistenciaID(1L));
		assertSame(asistencias, controller.c_obtenerAsistenciasPorCursoYFecha(1L, fecha));
		assertSame(asistencias, controller.c_obtenerAsistenciasPorEstudiante(2L));
		assertSame(asistencia, controller.c_modificarAsistencia(asistencia));
		assertEquals("Asistencia eliminada correctamente.", controller.c_borrarAsistencia(1L));
	}
}
