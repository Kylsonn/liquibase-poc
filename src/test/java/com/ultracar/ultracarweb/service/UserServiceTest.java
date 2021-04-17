package com.ultracar.ultracarweb.service;

import com.ultracar.ultracarweb.AbstractTest;
import com.ultracar.ultracarweb.controller.dto.UserCreateRequestDTO;
import com.ultracar.ultracarweb.model.User;
import com.ultracar.ultracarweb.repository.UserRepository;
import com.ultracar.ultracarweb.service.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest extends AbstractTest {
	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	@Test
	void findByUsernameWithExistentUserShouldReturnUser(){
		User user = createUser();
		when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));

		UserDTO userActual = userService.findByUsername("username");

		verify(userRepository).findByUsername(any());
		assertThat(userActual.getId()).isEqualTo(user.getId());
	}

	@Test
	void findByUsernameWithNonExistentUserShouldReturnNull(){
		when(userRepository.findByUsername(any())).thenReturn(Optional.empty());

		UserDTO userActual = userService.findByUsername("username");

		verify(userRepository).findByUsername(any());
		assertThat(userActual).isNull();
	}

	@Test
	void deleteWithExistentUserShouldDeleteUser(){
		userService.delete(1L);

		verify(userRepository).deleteById(any());
	}

	@Test
	void deleteWithNonExistentUserShouldThrowException(){
		doThrow(EmptyResultDataAccessException.class)
			.when(userRepository).deleteById(any());

		assertThrows(EntityNotFoundException.class, ()->{
			userService.delete(1L);
		});

		verify(userRepository).deleteById(any());
	}

	@Test
	void createUserWithValidParametersShouldCreateAndReturnUser(){
		UserCreateRequestDTO userCreateRequestDTO = createUserCreateRequestDTO();
		User userCreated = createUser(userCreateRequestDTO);

		when(userRepository.save(any())).thenReturn(userCreated);

		UserDTO actualUser = userService.create(userCreateRequestDTO);

		assertThat(actualUser.getId()).isEqualTo(userCreated.getId());
		assertThat(actualUser.getCreatedDate()).isNotNull();
		assertThat(actualUser.getLastAccess()).isNull();
		assertThat(actualUser.isActive()).isTrue();
		assertThat(actualUser.isEmailChecked()).isFalse();
		assertThat(actualUser.getUsername()).isEqualTo(userCreateRequestDTO.getUsername());
	}

	private User createUser(UserCreateRequestDTO userCreateRequestDTO){
		return User.builder()
			.active(Boolean.TRUE)
			.createdDate(new Date())
			.emailChecked(Boolean.FALSE)
			.id(1L)
			.username(userCreateRequestDTO.getUsername())
			.password(userCreateRequestDTO.getPassword())
			.build();
	}

	private UserCreateRequestDTO createUserCreateRequestDTO(){
		return UserCreateRequestDTO.builder()
			.password("abCD!1234")
			.username("username")
			.build();
	}
}
