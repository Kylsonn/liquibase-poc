package com.ultracar.ultracarweb.controller;

import com.ultracar.ultracarweb.model.User;
import com.ultracar.ultracarweb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/user", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	@GetMapping
	public List<User> getUsers(){
		return userService.getUsers();
	}
}
