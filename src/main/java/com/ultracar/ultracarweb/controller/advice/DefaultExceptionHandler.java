package com.ultracar.ultracarweb.controller.advice;

import com.ultracar.ultracarweb.controller.dto.ApiErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.time.Instant;

@ControllerAdvice
public class DefaultExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<ApiErrorDTO> handleException(EntityNotFoundException e) {
		return responseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity<ApiErrorDTO> handleException(MethodArgumentNotValidException e) {
		return responseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler
	public ResponseEntity<ApiErrorDTO> handleException(EntityExistsException e) {
		return responseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

	private ResponseEntity<ApiErrorDTO> responseEntity(String message, HttpStatus status) {
		ApiErrorDTO error = ApiErrorDTO.builder()
			.code(status)
			.message(message)
			.timestamp(Instant.now())
			.build();
		return ResponseEntity.badRequest()
			.contentType(MediaType.APPLICATION_JSON)
			.body(error);
	}
}
