package ch.fhnw.webfr.flashcard.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.fhnw.webfr.flashcard.domain.Questionnaire;
import ch.fhnw.webfr.flashcard.persistence.QuestionnaireRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(QuestionnaireController.class)
public class QuestionnaireControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private QuestionnaireRepository questionnaireRepositoryMock;

	@Before
	public void setUp() {
		Mockito.reset(questionnaireRepositoryMock);
	}

	@Test
	public void testFindAll() throws Exception {
		Questionnaire questionnaire = new Questionnaire();
		questionnaire.setId("id");
		questionnaire.setTitle("title");
		questionnaire.setDescription("description");
		when(questionnaireRepositoryMock.findAll()).thenReturn(Arrays.asList(questionnaire, new Questionnaire()));
		mockMvc.perform(get("/questionnaires")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is("id"))).andExpect(jsonPath("$[0].title", is("title")))
				.andExpect(jsonPath("$[0].description", is("description")));
		Mockito.verify(questionnaireRepositoryMock, times(1)).findAll();
	}

	@Test
	public void testCreate() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		Questionnaire questionnaire = new Questionnaire();
		questionnaire.setId("id");
		questionnaire.setTitle("title");
		questionnaire.setDescription("description");
		when(questionnaireRepositoryMock.save(questionnaire)).thenReturn(questionnaire);
		mockMvc.perform(post("/questionnaires").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(questionnaire))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", is("id"))).andExpect(jsonPath("$.title", is("title")))
				.andExpect(jsonPath("$.description", is("description")));
		Mockito.verify(questionnaireRepositoryMock, times(1)).save(questionnaire);
	}

	@Test
	public void testUpdate() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		Questionnaire questionnaire = new Questionnaire();
		questionnaire.setId("id");
		questionnaire.setTitle("title");
		questionnaire.setDescription("description");
		when(questionnaireRepositoryMock.save(questionnaire)).thenReturn(questionnaire);
		mockMvc.perform(post("/questionnaires").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(questionnaire))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", is("id"))).andExpect(jsonPath("$.title", is("title")))
				.andExpect(jsonPath("$.description", is("description")));
		Mockito.verify(questionnaireRepositoryMock, times(1)).save(questionnaire);
	}

	@Test
	public void testDelete() throws Exception {
		mockMvc.perform(delete("/questionnaires/3")).andExpect(status().isNoContent());
		Mockito.verify(questionnaireRepositoryMock, times(1)).delete("3");
	}

}
