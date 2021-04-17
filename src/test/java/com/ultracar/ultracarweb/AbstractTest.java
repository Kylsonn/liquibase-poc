package com.ultracar.ultracarweb;

import com.ultracar.ultracarweb.model.User;
import com.ultracar.ultracarweb.service.dto.UserDTO;
import io.micrometer.core.instrument.util.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

public abstract class AbstractTest {
	protected UserDTO createUserDTO(){
		return UserDTO.builder()
			.active(Boolean.TRUE)
			.createdDate(new Date())
			.emailChecked(Boolean.TRUE)
			.id(1L)
			.lastAccess(new Date())
			.username(RandomStringUtils.randomAlphabetic(20))
			.build();
	}

	protected User createUser(){
		return User.builder()
			.active(Boolean.TRUE)
			.createdDate(new Date())
			.emailChecked(Boolean.TRUE)
			.id(1L)
			.lastAccess(new Date())
			.username(RandomStringUtils.randomAlphabetic(20))
			.password(RandomStringUtils.randomAlphabetic(20))
			.build();
	}

	protected String getMockFile(String mockFile) throws IOException {
		return IOUtils.toString(Objects.requireNonNull(Thread.currentThread().getContextClassLoader()
			.getResourceAsStream("mockfiles/"+mockFile)));
	}
}
