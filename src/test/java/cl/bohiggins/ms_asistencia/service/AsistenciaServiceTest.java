package cl.bohiggins.ms_asistencia.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
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

import cl.bohiggins.ms_asistencia.dto.AsistenciaCreateRequest;
import cl.bohiggins.ms_asistencia.entity.Asistencia;
import cl.bohiggins.ms_asistencia.entity.EstadoAsistencia;
import cl.bohiggins.ms_asistencia.repository.AsistenciaRepository;

@ExtendWith(MockitoExtension.class)
class AsistenciaServiceTest {

	@Mock
	private AsistenciaRepository repositorio;

	@InjectMocks
	private AsistenciaService servicio;

	@Test
	void guardarAsistencia_mapeaRequestYGuarda() {
		LocalDate fecha = LocalDate.of(2026, 5, 8);
		AsistenciaCreateRequest request = new AsistenciaCreateRequest(
				1L,
				2L,
				fecha,
				EstadoAsistencia.PRESENTE,
				"Sin observacion",
				"12345678-9"
		);
		when(repositorio.findByEstudianteIdAndFecha(2L, fecha)).thenReturn(Optional.empty());
		when(repositorio.save(any(Asistencia.class))).thenAnswer(invocation -> {
			Asistencia asistencia = invocation.getArgument(0);
			asistencia.setId(10L);
			return asistencia;
		});

		Asistencia resultado = servicio.guardarAsistencia(request);

		assertEquals(10L, resultado.getId());
		assertEquals(1L, resultado.getCursoId());
		assertEquals(2L, resultado.getEstudianteId());
		assertEquals(fecha, resultado.getFecha());
		assertEquals(EstadoAsistencia.PRESENTE, resultado.getEstado());
		assertEquals("Sin observacion", resultado.getObservacion());
		assertEquals("12345678-9", resultado.getRegistradaPor());
		verify(repositorio).save(any(Asistencia.class));
	}

	@Test
	void guardarAsistencia_duplicada_lanzaError() {
		LocalDate fecha = LocalDate.of(2026, 5, 8);
		AsistenciaCreateRequest request = new AsistenciaCreateRequest(
				1L,
				2L,
				fecha,
				EstadoAsistencia.PRESENTE,
				"Sin observacion",
				"12345678-9"
		);
		when(repositorio.findByEstudianteIdAndFecha(2L, fecha)).thenReturn(Optional.of(new Asistencia()));

		IllegalArgumentException error = assertThrows(
				IllegalArgumentException.class,
				() -> servicio.guardarAsistencia(request)
		);

		assertEquals("Ya existe asistencia del estudiante para esa fecha.", error.getMessage());
		verify(repositorio, never()).save(any(Asistencia.class));
	}

	@Test
	void obtenerAsistencias_retornaListaDelRepositorio() {
		List<Asistencia> asistencias = List.of(new Asistencia(), new Asistencia());
		when(repositorio.findAll()).thenReturn(asistencias);

		List<Asistencia> resultado = servicio.obtenerAsistencias();

		assertSame(asistencias, resultado);
		assertEquals(2, resultado.size());
	}

	@Test
	void obtenerAsistenciaID_retornaAsistenciaEncontrada() {
		Asistencia asistencia = new Asistencia();
		asistencia.setId(5L);
		when(repositorio.findById(5L)).thenReturn(Optional.of(asistencia));

		Asistencia resultado = servicio.obtenerAsistenciaID(5L);

		assertSame(asistencia, resultado);
	}

	@Test
	void obtenerAsistenciasPorCursoYFecha_retornaListaDelRepositorio() {
		LocalDate fecha = LocalDate.of(2026, 5, 8);
		List<Asistencia> asistencias = List.of(new Asistencia());
		when(repositorio.findByCursoIdAndFecha(1L, fecha)).thenReturn(asistencias);

		List<Asistencia> resultado = servicio.obtenerAsistenciasPorCursoYFecha(1L, fecha);

		assertSame(asistencias, resultado);
	}

	@Test
	void obtenerAsistenciasPorEstudiante_retornaListaDelRepositorio() {
		List<Asistencia> asistencias = List.of(new Asistencia());
		when(repositorio.findByEstudianteId(2L)).thenReturn(asistencias);

		List<Asistencia> resultado = servicio.obtenerAsistenciasPorEstudiante(2L);

		assertSame(asistencias, resultado);
	}

	@Test
	void modificarAsistencia_actualizaDatosSiExiste() {
		Asistencia actual = crearAsistencia(1L);
		Asistencia modificada = crearAsistencia(1L);
		modificada.setCursoId(3L);
		modificada.setEstudianteId(4L);
		modificada.setEstado(EstadoAsistencia.ATRASADO);
		modificada.setObservacion("Llego tarde");
		modificada.setRegistradaPor("98765432-1");
		when(repositorio.findById(1L)).thenReturn(Optional.of(actual));
		when(repositorio.save(any(Asistencia.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Asistencia resultado = servicio.modificarAsistencia(modificada);

		assertEquals(3L, resultado.getCursoId());
		assertEquals(4L, resultado.getEstudianteId());
		assertEquals(EstadoAsistencia.ATRASADO, resultado.getEstado());
		assertEquals("Llego tarde", resultado.getObservacion());
		assertEquals("98765432-1", resultado.getRegistradaPor());
		verify(repositorio).save(actual);
	}

	@Test
	void modificarAsistencia_noExiste_retornaNull() {
		Asistencia modificada = crearAsistencia(99L);
		when(repositorio.findById(99L)).thenReturn(Optional.empty());

		Asistencia resultado = servicio.modificarAsistencia(modificada);

		assertNull(resultado);
	}

	@Test
	void borrarAsistencia_eliminaPorId() {
		String resultado = servicio.borrarAsistencia(1L);

		assertEquals("Asistencia eliminada correctamente.", resultado);
		verify(repositorio).deleteById(1L);
	}

	private Asistencia crearAsistencia(Long id) {
		Asistencia asistencia = new Asistencia();
		asistencia.setId(id);
		asistencia.setCursoId(1L);
		asistencia.setEstudianteId(2L);
		asistencia.setFecha(LocalDate.of(2026, 5, 8));
		asistencia.setEstado(EstadoAsistencia.PRESENTE);
		asistencia.setObservacion("Sin observacion");
		asistencia.setRegistradaPor("12345678-9");
		return asistencia;
	}
}
