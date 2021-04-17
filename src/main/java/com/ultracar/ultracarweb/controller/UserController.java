package com.ultracar.ultracarweb.controller;

import com.ultracar.ultracarweb.controller.dto.UserCreateRequestDTO;
import com.ultracar.ultracarweb.service.UserService;
import com.ultracar.ultracarweb.service.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/user", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	@GetMapping("/{username}")
	public UserDTO getUsers(@PathVariable String username){
		return userService.findByUsername(username);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity deleteUser(@PathVariable Long id){
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserCreateRequestDTO userCreateRequestDTO){
		UserDTO response = userService.create(userCreateRequestDTO);
		URI location = URI.create(String.format("/user/%s", response.getUsername()));
		return ResponseEntity.created(location).body(response);
	}
}
