package com.careerit.ipl.user.domain;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class AppUser {
		
		@Id
		private String id;
		private String username;
		private String email;
		private String password;
		private String fullName;
}
