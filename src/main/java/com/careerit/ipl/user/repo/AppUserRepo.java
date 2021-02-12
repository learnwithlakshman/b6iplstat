package com.careerit.ipl.user.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.careerit.ipl.user.domain.AppUser;

public interface AppUserRepo extends MongoRepository<AppUser, String> {

			Optional<AppUser> findByEmail(String email);
}
