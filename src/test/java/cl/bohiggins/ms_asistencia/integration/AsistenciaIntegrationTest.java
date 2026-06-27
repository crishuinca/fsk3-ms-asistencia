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

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AsistenciaIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void addAsistencia_ok() throws Exception {
		mockMvc.perform(post("/api/v1/addAsistencia")
				.contentType(APPLICATION_JSON)
				.content("{\"cursoId\":1,\"estudianteId\":1,\"fecha\":\"2026-05-08\",\"estado\":\"PRESENTE\",\"observacion\":\"Sin observacion\",\"registradaPor\":\"12345678-9\"}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.estado").value("PRESENTE"));
	}

	@Test
	void asistenciaById_noExiste() throws Exception {
		mockMvc.perform(get("/api/v1/asistenciaByID/999"))
				.andExpect(status().isNotFound());
	}

	@Test
	void addAsistencia_luegoGet() throws Exception {
		String creado = mockMvc.perform(post("/api/v1/addAsistencia")
				.contentType(APPLICATION_JSON)
				.content("{\"cursoId\":1,\"estudianteId\":2,\"fecha\":\"2026-05-09\",\"estado\":\"ATRASADO\",\"observacion\":\"Llego tarde\",\"registradaPor\":\"12345678-9\"}"))
				.andExpect(status().isCreated())
				.andReturn()
				.getResponse()
				.getContentAsString();

		long id = new ObjectMapper().readTree(creado).get("id").asLong();

		mockMvc.perform(get("/api/v1/asistenciaByID/" + id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.estado").value("ATRASADO"));
	}
}
