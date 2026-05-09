package cl.bohiggins.ms_asistencia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI msAsistenciaOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("MS Asistencia - Libro de clases digital")
						.description("API del microservicio de asistencia y anotaciones del Colegio Bernardo OHiggins.")
						.version("1.0.0"));
	}
}
