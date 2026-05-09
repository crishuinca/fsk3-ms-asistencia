package cl.bohiggins.ms_asistencia.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, String>> negocio(IllegalArgumentException ex) {
		return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> validacion(MethodArgumentNotValidException ex) {
		StringBuilder msg = new StringBuilder();
		for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
			if (msg.length() > 0) {
				msg.append(" | ");
			}
			msg.append(fe.getField()).append(": ").append(fe.getDefaultMessage());
		}
		return ResponseEntity.badRequest().body(Map.of("error", msg.toString()));
	}
}
