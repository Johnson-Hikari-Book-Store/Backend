package com.bookhub.bookhub_backend.auth;

import com.bookhub.bookhub_backend.auth.dto.LoginDto;
import com.bookhub.bookhub_backend.auth.dto.RegisterDto;
import com.bookhub.bookhub_backend.auth.jwt.JwtService;
import com.bookhub.bookhub_backend.user.UserService;
import com.bookhub.bookhub_backend.user.dto.CreateNewUserRequestDto;
import com.bookhub.bookhub_backend.user.entities.UserEntity;
import com.bookhub.bookhub_backend.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    JwtService jwtService;
    @Autowired
    UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;

    public UserEntity register(RegisterDto registerDto) {
        return userService.createNewUser(registerDto);
    }
    public String loginUser(LoginDto loginDto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );
        UserEntity user = userService.getUserByEmail(loginDto.getEmail());
        return jwtService.generateJwtToken(user);
    }
}
