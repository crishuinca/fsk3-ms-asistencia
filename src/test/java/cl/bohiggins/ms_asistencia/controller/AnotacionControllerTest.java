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

import cl.bohiggins.ms_asistencia.dto.AnotacionCreateRequest;
import cl.bohiggins.ms_asistencia.entity.Anotacion;
import cl.bohiggins.ms_asistencia.entity.TipoAnotacion;
import cl.bohiggins.ms_asistencia.service.AnotacionService;

@ExtendWith(MockitoExtension.class)
class AnotacionControllerTest {

	@Mock
	private AnotacionService servicio;

	@InjectMocks
	private AnotacionController controller;

	@Test
	void anotacionController_delegaOperaciones() {
		LocalDate fecha = LocalDate.of(2026, 5, 8);
		Anotacion anotacion = new Anotacion();
		anotacion.setId(1L);
		AnotacionCreateRequest request = new AnotacionCreateRequest(
				1L,
				2L,
				fecha,
				TipoAnotacion.POSITIVA,
				"Participa activamente",
				"12345678-9"
		);
		List<Anotacion> anotaciones = List.of(anotacion);
		when(servicio.guardarAnotacion(request)).thenReturn(anotacion);
		when(servicio.obtenerAnotaciones()).thenReturn(anotaciones);
		when(servicio.obtenerAnotacionID(1L)).thenReturn(anotacion);
		when(servicio.obtenerAnotacionesPorEstudiante(2L)).thenReturn(anotaciones);
		when(servicio.obtenerAnotacionesPorCurso(1L)).thenReturn(anotaciones);
		when(servicio.obtenerAnotacionesPorTipo(TipoAnotacion.POSITIVA)).thenReturn(anotaciones);
		when(servicio.modificarAnotacion(anotacion)).thenReturn(anotacion);
		when(servicio.borrarAnotacion(1L)).thenReturn("Anotacion eliminada correctamente.");

		assertSame(anotacion, controller.c_guardarAnotacion(request));
		assertSame(anotaciones, controller.c_obtenerAnotaciones());
		assertSame(anotacion, controller.c_obtenerAnotacionID(1L));
		assertSame(anotaciones, controller.c_obtenerAnotacionesPorEstudiante(2L));
		assertSame(anotaciones, controller.c_obtenerAnotacionesPorCurso(1L));
		assertSame(anotaciones, controller.c_obtenerAnotacionesPorTipo(TipoAnotacion.POSITIVA));
		assertSame(anotacion, controller.c_modificarAnotacion(anotacion));
		assertEquals("Anotacion eliminada correctamente.", controller.c_borrarAnotacion(1L));
	}
}
