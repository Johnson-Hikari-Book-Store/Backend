package com.bookhub.bookhub_backend.user;

import com.bookhub.bookhub_backend.user.dto.CreateNewUserRequestDto;
import com.bookhub.bookhub_backend.user.entities.UserEntity;
import com.bookhub.bookhub_backend.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserEntity createNewUser (CreateNewUserRequestDto createNewUserRequestDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserEntity user = UserEntity.builder()
                .email(createNewUserRequestDto.getEmail())
                .name(createNewUserRequestDto.getName())
                .username(createNewUserRequestDto.getUsername())
                .password(passwordEncoder.encode( createNewUserRequestDto.getPassword()))
                .build();
        userRepository.save(user);
        return user;
    }

    public UserEntity getUserByEmail (String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(
                        String.format("User with email: %s, not found", email))
        );
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }
}
