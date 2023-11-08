package com.br.authserver.model;

import java.io.Serializable;
import java.util.UUID;

import com.br.authserver.dtos.SignUpDto;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_USERS")
@Data
@Builder
public class UserModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	private String username;
	private String password;
	private String realName;
	private String email;
	private String imgUser;
	private String date;
	
	public UserModel(SignUpDto signUpDto) {
		this.realName = signUpDto.realName();
		this.username = signUpDto.username();
		this.password = signUpDto.password();
		this.email = signUpDto.email();
		this.date = signUpDto.date();
	}
	
	
	
	
}
