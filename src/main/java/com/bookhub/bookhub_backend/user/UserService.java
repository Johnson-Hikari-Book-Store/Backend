package com.bookhub.bookhub_backend.user;

import com.bookhub.bookhub_backend.user.dto.CreateNewUserRequestDto;
import com.bookhub.bookhub_backend.user.dto.UpdateUserDto;
import com.bookhub.bookhub_backend.user.entities.UserDetailsEntity;
import com.bookhub.bookhub_backend.user.entities.UserEntity;
import com.bookhub.bookhub_backend.user.repositories.UserDetailsRepository;
import com.bookhub.bookhub_backend.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDetailsRepository userDetailsRepository;

    public UserEntity createNewUser(CreateNewUserRequestDto createNewUserRequestDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserEntity user = UserEntity.builder()
                .email(createNewUserRequestDto.getEmail())
                .name(createNewUserRequestDto.getName())
                .username(createNewUserRequestDto.getUsername())
                .password(passwordEncoder.encode(createNewUserRequestDto.getPassword()))
                .build();
        UserDetailsEntity userDetails = UserDetailsEntity.builder()
                .address(createNewUserRequestDto.getAddress())
                .country(createNewUserRequestDto.getCountry())
                .state(createNewUserRequestDto.getState())
                .phoneNum(createNewUserRequestDto.getPhoneNum())
                .profileImage(createNewUserRequestDto.getProfileImage())
                .preferredCommunicationMeans(createNewUserRequestDto.getPreferredCommunicationMeans())
                .build();
        userDetailsRepository.save(userDetails);
        user.setUserDetails(userDetails);
        userRepository.save(user);
        return user;
    }

    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(
                        String.format("User with email: %s, not found", email))
        );
    }

    public UserEntity getSingleUser(String userId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get();
        } else {
//            throw  new Exception("User Not Found Exception");
            return null;
        }
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity updateSingleUser(String userId, UpdateUserDto updateUserDto) {
        UserEntity user = getSingleUser(userId);
        UserDetailsEntity userDetails = userDetailsRepository.findById(user.getUserDetails().getId()).get();

        Map<String, Object> userFields = new HashMap<>();
        userFields.put("name", updateUserDto.getName());
        userFields.put("email", updateUserDto.getEmail());
        userFields.put("password", updateUserDto.getPassword());
        userFields.put("username", updateUserDto.getUsername());

        Map<String, Object> userDetailsFields = new HashMap<>();
        userDetailsFields.put("country", updateUserDto.getCountry());
        userDetailsFields.put("address", updateUserDto.getAddress());
        userDetailsFields.put("phoneNum", updateUserDto.getPhoneNum());
        userDetailsFields.put("state", updateUserDto.getState());
        userDetailsFields.put("profileImage", updateUserDto.getProfileImage());
        userDetailsFields.put("preferredCommunicationMeans", updateUserDto.getPrefferedCommunicationMeans());

        userDetailsFields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(UserDetailsEntity.class, key);
            if( value != null) {
                assert field != null;
                field.setAccessible(true);
                ReflectionUtils.setField(field, user, value);
            }
        });
        userFields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(UserEntity.class, key);
            if( value != null) {
                assert field != null;
                field.setAccessible(true);
                ReflectionUtils.setField(field, user, value);
            }
        });
        userDetailsRepository.save(userDetails);
        user.setUserDetails(userDetails);
        userRepository.save(user);
        return user;
    }

    public void deleteSingleUser(String userId) {
        userRepository.deleteById(userId);
    }
}
