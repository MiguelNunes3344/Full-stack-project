package com.br.authserver.test;



import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import java.nio.CharBuffer;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import com.br.authserver.dtos.CredentialsDto;
import com.br.authserver.dtos.SignUpDto;
import com.br.authserver.dtos.UserDto;
import com.br.authserver.exceptions.AppException;
import com.br.authserver.model.UserModel;
import com.br.authserver.repository.UserRepository;
import com.br.authserver.services.UserService;


@ActiveProfiles("test")
public class UserServiceTest {
	
	@Mock
	UserRepository userRepository;
	
	@Mock
    PasswordEncoder passwordEncoder;
	
	@Mock
	UserModel userModel;
	
	@InjectMocks
	Optional<UserModel> optional;
	@Mock
	SignUpDto signUpDto;
	
	@InjectMocks
	UserService userService;
	
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		optional = Optional.of(new UserModel(UUID.randomUUID(),"username","password","realName","email","imgUser","date"));
		signUpDto = new SignUpDto("realName","username","password","email","date","img");
		userModel = new UserModel(UUID.randomUUID(),"username","password","realName","email","imgUser","date");
	}
	
	
	
	
	
	
	
	
	
	
	
	@Test
	void loginSuccess() {
		CredentialsDto credentialsDto = new CredentialsDto("username","password");
		
		Optional<UserModel> user = Optional.ofNullable(new UserModel());
		
		
		when(userRepository.findByUsername("username")).thenReturn(user);
		when(passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()), user.get().getPassword())).thenReturn(true);
		UserDto userDto =userService.login(credentialsDto);
		verify(userRepository).findByUsername(credentialsDto.username()); //Verifica se o metodo foi chamado
		verifyNoMoreInteractions(userRepository); // Verifica se o metodo não foi chamado mais que uma ves
		
		
	}
	@Test
	void loginFailPassword() {
		CredentialsDto credentialsDto = new CredentialsDto("username","password");
		
		Optional<UserModel> user = Optional.ofNullable(new UserModel());
		
		
		when(userRepository.findByUsername("username")).thenReturn(user);
		when(passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()), user.get().getPassword())).thenReturn(false);
		
		
		Exception e =Assertions.assertThrows(AppException.class, () -> { // Verifica se a exceção foi lançada
			userService.login(credentialsDto);
		});	
		assertEquals("Invalid password", e.getMessage()); // verifica se a mensagem corresponde a mensagem da exceção
		
		
	}
	@Test
	void loginFailUsername() {
		CredentialsDto credentialsDto = new CredentialsDto("username","password");
		
		Optional<UserModel> user = Optional.ofNullable(new UserModel());
		
		
		when(userRepository.findByUsername("usernam")).thenReturn(user);
		when(passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()), user.get().getPassword())).thenReturn(true);
		
		
		Exception e =Assertions.assertThrows(AppException.class, () -> { // Verifica se a exceção foi lançada
			userService.login(credentialsDto);
		});	
		assertEquals("Unknown user", e.getMessage()); // verifica se a mensagem corresponde a mensagem da exceção
		
		
	}

	

	
	
		
}
