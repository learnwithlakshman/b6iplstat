package com.careerit.ipl.user.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.careerit.ipl.user.domain.AppUser;
import com.careerit.ipl.user.repo.AppUserRepo;
import com.careerit.ipl.user.service.exception.UserAlreadyExistsException;

@Service
public class AppUserServiceImpl implements AppUserService {

	private static final Logger log = LoggerFactory.getLogger(AppUserServiceImpl.class);
	@Autowired
	private AppUserRepo appUserRepo;

	@Override
	public AppUser registerUser(AppUser appUser) {
		Assert.notNull(appUser, "Register user object can not be empty or null");
		Assert.notNull(appUser.getEmail(), "Email can not be null or empty");
		Assert.notNull(appUser.getPassword(), "Password can not be null or empty");
		Assert.notNull(appUser.getUsername(), "Username can not be null or empty");

		Optional<AppUser> optUser = appUserRepo.findByEmail(appUser.getEmail());

		if (!optUser.isPresent()) {
			AppUser savedUser = appUserRepo.save(appUser);
			log.info("User registred with id:{} successfully", savedUser.getId());
			return savedUser;
		}
		log.warn("User with email {} already exists", appUser.getEmail());
		throw new UserAlreadyExistsException(
				String.format("With email : %s user already user exists", appUser.getEmail()));
	}

}
