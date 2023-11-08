package com.br.authserver.test;

import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import com.br.authserver.controller.Controller;
import com.br.authserver.dtos.SignUpDto;
import com.br.authserver.exceptions.AppException;
import com.br.authserver.model.UserModel;
import com.br.authserver.repository.UserRepository;
import com.br.authserver.services.UserService;


@ActiveProfiles("test")
public class RegisterTest {
	
	@Mock
	UserRepository userRepository;
	
	@InjectMocks
	private UserService userService;
	
	@InjectMocks
	private Controller controller;
	
	UserModel userModel;
	
	Optional<UserModel> optionalUserModel;
	
	SignUpDto signUpDto;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		signUpDto = new SignUpDto("realName",  "username",  "password", "email", "date","imgUser");
		optionalUserModel = Optional.of(new UserModel(UUID.fromString("7df89a30-8bad-4339-a12c-73d5b64f02f2"), "realName",  "username",  "password", "email", "date","imgUser"));
		userModel = new UserModel(UUID.fromString("7df89a30-8bad-4339-a12c-73d5b64f02f2"), "realName",  "username",  "password", "email", "date","imgUser");
	}
	
	
	@Test
	void register(){
		when(userRepository.findByUsername(Mockito.anyString())).thenReturn(optionalUserModel);
		when(userRepository.save(Mockito.any(UserModel.class))).thenReturn(userModel);
		userService.register(signUpDto);
		
	}
	@Test
	void registerFail(){
		when(userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.empty());
		Exception throwed =Assertions.assertThrows(AppException.class, () -> {
			userService.register(signUpDto);
		});
		Assertions.assertEquals("Login already exists", throwed.getMessage());
		
	}

}
