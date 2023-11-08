package com.br.authserver.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.authserver.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, UUID> {
	Optional<UserModel> findByUsername(String login);
}
