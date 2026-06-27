package cl.bohiggins.ms_asistencia.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class GlobalExceptionHandlerTest {

	private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

	@Test
	void noEncontrado_retorna404() {
		ResponseEntity<Map<String, String>> respuesta = handler
				.noEncontrado(new RecursoNoEncontradoException("Asistencia no encontrada."));

		assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
		assertNotNull(respuesta.getBody());
		assertEquals("Asistencia no encontrada.", respuesta.getBody().get("error"));
	}

	@Test
	void negocio_retornaBadRequest() {
		ResponseEntity<Map<String, String>> respuesta = handler.negocio(new IllegalArgumentException("Dato invalido."));

		assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
		assertEquals("Dato invalido.", respuesta.getBody().get("error"));
	}

	@Test
	void interno_retorna500() {
		ResponseEntity<Map<String, String>> respuesta = handler.interno(new RuntimeException("fallo"));

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, respuesta.getStatusCode());
		assertEquals("Error interno del servidor.", respuesta.getBody().get("error"));
	}
}
