package com.careerit.ipl.web;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ApiError {

		private final String message;
		private final HttpStatus httpStatus;
		private final LocalDateTime dateTime;
}
