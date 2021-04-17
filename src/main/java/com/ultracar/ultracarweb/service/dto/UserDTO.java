package com.ultracar.ultracarweb.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ultracar.ultracarweb.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.Instant;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
	private Long id;
	@NotEmpty
	@Pattern(regexp = User.USERNAME_PATTERN, message = "Invalid username")
	private String username;
	Date createdDate;
	private boolean active ;
	private boolean emailChecked;
	private Date lastAccess;
}
