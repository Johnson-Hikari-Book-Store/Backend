package com.bookhub.bookhub_backend.auth;

import com.bookhub.bookhub_backend.auth.dto.LoginDto;
import com.bookhub.bookhub_backend.auth.dto.RegisterDto;
import com.bookhub.bookhub_backend.user.entities.UserEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(
            @Valid @RequestBody RegisterDto registerDto
            ) {
        Map<String, Object> response = new HashMap<>();
        UserEntity user = authService.register(registerDto);
        LoginDto loginDto = new LoginDto(user.getEmail(), user.getPassword());
        String token = authService.loginUser(loginDto);
        response.put("success", true);
        response.put("message", "User Logged in successfully");
        response.put("data" , user);
        response.put("token", token);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>>login(
            @Valid @RequestBody LoginDto loginDto,
            Authentication authentication
    ) {
        String token = authService.loginUser(loginDto);
        UserEntity user = (UserEntity) authentication.getDetails();
        System.out.println(user);
        Map<String, Object> response = new HashMap<>();
        response.put("success" , true);
        response.put("message", "User Logged in successfully");
        response.put("data" , user);
        response.put("token", token);
        return ResponseEntity.ok(response);
    }
}
