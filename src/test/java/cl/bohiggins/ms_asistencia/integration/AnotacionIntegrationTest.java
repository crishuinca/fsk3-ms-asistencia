package cl.bohiggins.ms_asistencia.integration;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AnotacionIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void addAnotacion_ok() throws Exception {
		mockMvc.perform(post("/api/v1/addAnotacion")
				.contentType(APPLICATION_JSON)
				.content("{\"cursoId\":1,\"estudianteId\":1,\"fecha\":\"2026-05-08\",\"tipo\":\"POSITIVA\",\"descripcion\":\"Participo en clase\",\"registradaPor\":\"12345678-9\"}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.tipo").value("POSITIVA"));
	}

	@Test
	void anotacionById_noExiste() throws Exception {
		mockMvc.perform(get("/api/v1/anotacionByID/999"))
				.andExpect(status().isNotFound());
	}
}
