package com.sistem_bank.fibank.mapper;

import com.sistem_bank.fibank.domain.User;
import com.sistem_bank.fibank.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

/*
When use @Mapper(componentModel = "spring"), MapStruct will generate the implementation of the
mapper interface as a Spring Bean. This means you can inject the mapper into other Spring-managed beans,
such as services and controllers, without manually creating instances of the mapper.
 */

@Mapper(componentModel = "spring")
public interface UserMapper {
    //mapping for signup feature
    User toEntity(SignupRequest signupRequest);
    @Mapping (target = "message", constant = "registration completed successfully")
    SignupResponse toSignupResponse(User user);

    //mapping for signin feature;
    User toEntity(LoginRequest loginRequest);
    LoginResponse toLoginResponse(User user);

    //map to a userdto
    UserDTO toUserDTO(User user);

    //mapping to a userdto list
    List<UserDTO> toUserDTO(List<User>users);
}
