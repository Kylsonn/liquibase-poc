package com.ultracar.ultracarweb.service;

import com.ultracar.ultracarweb.controller.dto.UserCreateRequestDTO;
import com.ultracar.ultracarweb.model.User;
import com.ultracar.ultracarweb.repository.UserRepository;
import com.ultracar.ultracarweb.service.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.sql.SQLException;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	public UserDTO findByUsername(String username){
		return userRepository.findByUsername(username).map(this::toUserDTO).orElse(null);
	}

	public UserDTO create(UserCreateRequestDTO userCreateRequestDTO){
		try {
			return toUserDTO(userRepository.save(User.builder()
				.username(userCreateRequestDTO.getUsername())
				.password(userCreateRequestDTO.getPassword())
				.active(Boolean.TRUE)
				.emailChecked(Boolean.FALSE)
				.createdDate(new Date())
				.build())
			);
		}catch (DataIntegrityViolationException ex) {
			if (ex.getRootCause() instanceof SQLException) {
				if (((SQLException) ex.getRootCause()).getSQLState().equals("23505")) {
					throw new EntityExistsException("User already exists: " + userCreateRequestDTO.getUsername());
				} else {
					throw ex;
				}
			} else {
				throw ex;
			}
		}
	}

	public void delete(Long id){
		try {
			userRepository.deleteById(id);
		}catch (EmptyResultDataAccessException ex){
			throw new EntityNotFoundException("Could not find user: " + id);
		}
	}

	private UserDTO toUserDTO(User user){
		return UserDTO.builder()
			.active(user.isActive())
			.createdDate(user.getCreatedDate())
			.emailChecked(user.isEmailChecked())
			.id(user.getId())
			.lastAccess(user.getLastAccess())
			.username(user.getUsername())
			.build();
	}
}
