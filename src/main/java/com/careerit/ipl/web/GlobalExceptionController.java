package com.careerit.ipl.web;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.careerit.ipl.user.service.exception.UserAlreadyExistsException;

@ControllerAdvice
public class GlobalExceptionController extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ UserAlreadyExistsException.class })
	public ResponseEntity<Object> hadleConfilt(RuntimeException ex, WebRequest request) {
		ApiError api = new ApiError(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now());
		return handleExceptionInternal(ex, api, new HttpHeaders(), HttpStatus.CONFLICT, request);

	}
}
