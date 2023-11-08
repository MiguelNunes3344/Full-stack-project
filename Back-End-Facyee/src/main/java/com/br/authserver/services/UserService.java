package com.br.authserver.services;

import java.nio.CharBuffer;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.br.authserver.dtos.CredentialsDto;
import com.br.authserver.dtos.SignUpDto;
import com.br.authserver.dtos.UserDto;
import com.br.authserver.exceptions.AppException;
import com.br.authserver.model.UserModel;
import com.br.authserver.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	
	 	private final UserRepository userRepository;

	    private final PasswordEncoder passwordEncoder;

	    

	    public UserDto login(CredentialsDto credentialsDto) {
	        UserModel user = userRepository.findByUsername(credentialsDto.username())
	                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

	        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()), user.getPassword())) {
	        	UserDto userDto = new UserDto();
	        	BeanUtils.copyProperties(user, userDto);
	            return (userDto);
	        }
	        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
	    }



	    public UserDto register(SignUpDto signUpDto)  {
	        Optional<UserModel> optionalUser = userRepository.findByUsername(signUpDto.username());
	        if (optionalUser.isPresent()) {
	            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
	        }
	        
	        UserModel user = new UserModel(signUpDto);
	        user = userRepository.save(user);
	        UserDto userDto = new UserDto(user);
	 
	        return userDto;
	    }
}
