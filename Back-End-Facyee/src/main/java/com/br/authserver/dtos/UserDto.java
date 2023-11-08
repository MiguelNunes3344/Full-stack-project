package com.br.authserver.dtos;

import java.util.UUID;

import com.br.authserver.model.UserModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	private UUID id;
	private String realName;
	private String email;
	private String username;
	private String token;
	
	
	public UserDto(UserModel userModel) {
		this.id = userModel.getId();
		this.realName = userModel.getRealName();
		this.email = userModel.getEmail();
		this.username = userModel.getUsername();
	
	}
	
}
