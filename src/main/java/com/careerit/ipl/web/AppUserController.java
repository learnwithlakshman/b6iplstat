package com.careerit.ipl.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.careerit.ipl.user.domain.AppUser;
import com.careerit.ipl.user.service.AppUserService;

@RestController
@RequestMapping("/api/user/")
public class AppUserController {

	@Autowired
	private AppUserService appUserService;

	@PostMapping
	public AppUser register(@RequestBody AppUser appUser) {
		return appUserService.registerUser(appUser);
	}
}
