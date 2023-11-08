package com.br.authserver.controller;

import java.net.URI;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.br.authserver.config.UserAuthProvider;
import com.br.authserver.dtos.CredentialsDto;
import com.br.authserver.dtos.SignUpDto;
import com.br.authserver.dtos.TokenDto;
import com.br.authserver.dtos.UserDto;
import com.br.authserver.services.UserService;




@CrossOrigin(origins = "http://localhost:4200/")
@RestController
public class Controller {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserAuthProvider userAuth;
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/processimage")
	public ResponseEntity<?> processimage(@RequestParam("image") MultipartFile file) {
		return ResponseEntity.ok().body(file.getOriginalFilename());
		
	}
	
	
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/verifyToken")
	public ResponseEntity<Boolean> register(@RequestBody TokenDto tokenDto) {
		Authentication auth = userAuth.validateToken(tokenDto.token());
		return ResponseEntity.ok().body(auth.isAuthenticated());
		
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/obterTokenCSRF")
	public CsrfToken obterTokenCSRF(CsrfToken token) {
        return token;
    }
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/login")
	public ResponseEntity<UserDto> login(@RequestBody CredentialsDto credentialsDto) {
		UserDto user = userService.login(credentialsDto);
		user.setToken(userAuth.createToken(user));
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
		
	}
	
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/register")
	public ResponseEntity<UserDto> register(@RequestBody SignUpDto signUpDto) {
		UserDto user = userService.register(signUpDto);
		user.setToken(userAuth.createToken(user));
		return ResponseEntity.created(URI.create("/users/" + user.getId())).body(user);
		
	}
	
	
	
	

	
}
