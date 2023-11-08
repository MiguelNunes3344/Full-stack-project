package com.br.authserver.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.springframework.security.core.userdetails.User;

import com.br.authserver.dtos.SignUpDto;
import com.br.authserver.dtos.UserDto;
import com.br.authserver.model.UserModel;

@Mapper(componentModel = "spring")
@MapperConfig
public interface UserMapper {

    UserDto toUserDto(UserModel user);

    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDto signUpDto);

}
