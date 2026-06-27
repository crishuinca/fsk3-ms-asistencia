package cl.bohiggins.ms_asistencia.web;

import cl.bohiggins.ms_asistencia.exception.RecursoNoEncontradoException;

public final class RecursoHttp {

	private RecursoHttp() {
	}

	public static <T> T requerir(T valor, String mensaje) {
		if (valor == null) {
			throw new RecursoNoEncontradoException(mensaje);
		}
		return valor;
	}
}
