package cl.bohiggins.ms_asistencia.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.bohiggins.ms_asistencia.dto.AnotacionCreateRequest;
import cl.bohiggins.ms_asistencia.entity.Anotacion;
import cl.bohiggins.ms_asistencia.entity.TipoAnotacion;
import cl.bohiggins.ms_asistencia.repository.AnotacionRepository;

@ExtendWith(MockitoExtension.class)
class AnotacionServiceTest {

	@Mock
	private AnotacionRepository repositorio;

	@InjectMocks
	private AnotacionService servicio;

	@Test
	void guardarAnotacion_mapeaRequestYGuarda() {
		AnotacionCreateRequest request = new AnotacionCreateRequest(
				1L,
				2L,
				LocalDate.of(2026, 5, 8),
				TipoAnotacion.POSITIVA,
				"Participa activamente",
				"12345678-9"
		);
		when(repositorio.save(any(Anotacion.class))).thenAnswer(invocation -> {
			Anotacion anotacion = invocation.getArgument(0);
			anotacion.setId(10L);
			return anotacion;
		});

		Anotacion resultado = servicio.guardarAnotacion(request);

		assertEquals(10L, resultado.getId());
		assertEquals(1L, resultado.getCursoId());
		assertEquals(2L, resultado.getEstudianteId());
		assertEquals(LocalDate.of(2026, 5, 8), resultado.getFecha());
		assertEquals(TipoAnotacion.POSITIVA, resultado.getTipo());
		assertEquals("Participa activamente", resultado.getDescripcion());
		assertEquals("12345678-9", resultado.getRegistradaPor());
		verify(repositorio).save(any(Anotacion.class));
	}

	@Test
	void obtenerAnotaciones_retornaListaDelRepositorio() {
		List<Anotacion> anotaciones = List.of(new Anotacion(), new Anotacion());
		when(repositorio.findAll()).thenReturn(anotaciones);

		List<Anotacion> resultado = servicio.obtenerAnotaciones();

		assertSame(anotaciones, resultado);
		assertEquals(2, resultado.size());
	}

	@Test
	void obtenerAnotacionID_retornaAnotacionEncontrada() {
		Anotacion anotacion = new Anotacion();
		anotacion.setId(5L);
		when(repositorio.findById(5L)).thenReturn(Optional.of(anotacion));

		Anotacion resultado = servicio.obtenerAnotacionID(5L);

		assertSame(anotacion, resultado);
	}

	@Test
	void obtenerAnotacionesPorEstudiante_retornaListaDelRepositorio() {
		List<Anotacion> anotaciones = List.of(new Anotacion());
		when(repositorio.findByEstudianteId(2L)).thenReturn(anotaciones);

		List<Anotacion> resultado = servicio.obtenerAnotacionesPorEstudiante(2L);

		assertSame(anotaciones, resultado);
	}

	@Test
	void obtenerAnotacionesPorCurso_retornaListaDelRepositorio() {
		List<Anotacion> anotaciones = List.of(new Anotacion());
		when(repositorio.findByCursoId(1L)).thenReturn(anotaciones);

		List<Anotacion> resultado = servicio.obtenerAnotacionesPorCurso(1L);

		assertSame(anotaciones, resultado);
	}

	@Test
	void obtenerAnotacionesPorTipo_retornaListaDelRepositorio() {
		List<Anotacion> anotaciones = List.of(new Anotacion());
		when(repositorio.findByTipo(TipoAnotacion.NEGATIVA)).thenReturn(anotaciones);

		List<Anotacion> resultado = servicio.obtenerAnotacionesPorTipo(TipoAnotacion.NEGATIVA);

		assertSame(anotaciones, resultado);
	}

	@Test
	void modificarAnotacion_actualizaDatosSiExiste() {
		Anotacion actual = crearAnotacion(1L);
		Anotacion modificada = crearAnotacion(1L);
		modificada.setCursoId(3L);
		modificada.setEstudianteId(4L);
		modificada.setTipo(TipoAnotacion.NEGATIVA);
		modificada.setDescripcion("No presenta tarea");
		modificada.setRegistradaPor("98765432-1");
		when(repositorio.findById(1L)).thenReturn(Optional.of(actual));
		when(repositorio.save(any(Anotacion.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Anotacion resultado = servicio.modificarAnotacion(modificada);

		assertEquals(3L, resultado.getCursoId());
		assertEquals(4L, resultado.getEstudianteId());
		assertEquals(TipoAnotacion.NEGATIVA, resultado.getTipo());
		assertEquals("No presenta tarea", resultado.getDescripcion());
		assertEquals("98765432-1", resultado.getRegistradaPor());
		verify(repositorio).save(actual);
	}

	@Test
	void modificarAnotacion_noExiste_retornaNull() {
		Anotacion modificada = crearAnotacion(99L);
		when(repositorio.findById(99L)).thenReturn(Optional.empty());

		Anotacion resultado = servicio.modificarAnotacion(modificada);

		assertNull(resultado);
	}

	@Test
	void borrarAnotacion_eliminaPorId() {
		String resultado = servicio.borrarAnotacion(1L);

		assertEquals("Anotacion eliminada correctamente.", resultado);
		verify(repositorio).deleteById(1L);
	}

	private Anotacion crearAnotacion(Long id) {
		Anotacion anotacion = new Anotacion();
		anotacion.setId(id);
		anotacion.setCursoId(1L);
		anotacion.setEstudianteId(2L);
		anotacion.setFecha(LocalDate.of(2026, 5, 8));
		anotacion.setTipo(TipoAnotacion.POSITIVA);
		anotacion.setDescripcion("Participa activamente");
		anotacion.setRegistradaPor("12345678-9");
		return anotacion;
	}
}
