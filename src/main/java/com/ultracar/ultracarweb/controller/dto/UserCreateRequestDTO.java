package com.ultracar.ultracarweb.controller.dto;

import com.ultracar.ultracarweb.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
public class UserCreateRequestDTO {
	@NotEmpty
	@Pattern(regexp = User.USERNAME_PATTERN, message = "Invalid username")
	private String username;

	@NotEmpty
	@Pattern(regexp = User.PASSWORD_PATTERN, message = "Invalid password")
	private String password;

}
