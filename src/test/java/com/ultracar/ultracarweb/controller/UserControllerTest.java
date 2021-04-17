package com.ultracar.ultracarweb.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ultracar.ultracarweb.AbstractTest;
import com.ultracar.ultracarweb.controller.dto.UserCreateRequestDTO;
import com.ultracar.ultracarweb.model.User;
import com.ultracar.ultracarweb.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerTest extends AbstractTest {
	@Autowired
	private MockMvc mvc;

	@Autowired
	private UserRepository userRepository;

	private static ObjectMapper mapper = new ObjectMapper()
		.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

	private static final String BASE_URI = "/user";

	@Test
	void createUserWithValidParametersShouldCreateUser() throws Exception {
		mvc.perform(post(BASE_URI)
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(getMockFile("user-request.json")))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id", notNullValue()));
	}

	@Test
	void createUserWithInValidParametersShouldNotCreateUser() throws Exception {
		mvc.perform(post(BASE_URI)
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(getMockFile("user-request-invalid-password.json")))
			.andExpect(status().isBadRequest());

		mvc.perform(post(BASE_URI)
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(getMockFile("user-request-invalid-username.json")))
			.andExpect(status().isBadRequest());

		mvc.perform(post(BASE_URI)
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{}"))
			.andExpect(status().isBadRequest());

		mvc.perform(post(BASE_URI)
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"username\":\"username_1\"}"))
			.andExpect(status().isBadRequest());

		mvc.perform(post(BASE_URI)
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"password\":\"abCD!1234\"}"))
			.andExpect(status().isBadRequest());
	}

	@Test
	void createUserWithExistentUsernameShouldNotCreateUser() throws Exception {
		User user = insertUserDB();
		UserCreateRequestDTO userCreateRequestDTO = UserCreateRequestDTO.builder()
			.password(user.getPassword())
			.username(user.getUsername())
			.build();
		mvc.perform(post(BASE_URI)
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(userCreateRequestDTO)))
			.andExpect(status().isBadRequest());

	}

	@Test
	void deleteUserWithValidParameterShouldDeleteUser() throws Exception {
		User user = insertUserDB();
		mvc.perform(delete(BASE_URI+"/"+user.getId())
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());

		assertThat(userRepository.findById(user.getId())).isEmpty();
	}

	@Test
	void deleteUserWithNonExistentIdShouldReturnBadRequest() throws Exception {
		mvc.perform(delete(BASE_URI+"/-1")
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}

	@Test
	void getUserWithValidUsernameShouldReturnUser() throws Exception {
		User user = insertUserDB();
		MockHttpServletResponse response = mvc.perform(get(BASE_URI+"/"+user.getUsername())
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andReturn().getResponse();

		User actualUser = mapper.readValue(response.getContentAsString(), User.class);
		assertThat(actualUser.getId()).isEqualTo(user.getId());
	}

	@Test
	void getUserWithNonExistentUsernameShouldReturnEmpty() throws Exception {
		mvc.perform(get(BASE_URI+"/nobody")
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$").doesNotExist());
	}

	private User insertUserDB(){
		return userRepository.save(createUser());
	}
}
