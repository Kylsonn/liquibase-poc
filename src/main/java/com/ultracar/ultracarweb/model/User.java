package com.ultracar.ultracarweb.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Builder
@Entity
@Table(name = "tb_user")
@NoArgsConstructor
@AllArgsConstructor
public class User {
	public static final String USERNAME_PATTERN = "[a-zA-Z0-9@\\-_.]{5,50}";
	public static final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String password;
	@CreatedDate
	private Date createdDate;
	private boolean active;
	private boolean emailChecked;
	private Date lastAccess;
}
