package com.bookhub.bookhub_backend.user;

import com.bookhub.bookhub_backend.user.dto.CreateNewUserRequestDto;
import com.bookhub.bookhub_backend.user.entities.UserEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("")
    public UserEntity createNewUser ( @Valid @RequestBody CreateNewUserRequestDto createNewUserRequestDto) {
        return userService.createNewUser(createNewUserRequestDto);
    }

    @GetMapping("")
    public List<UserEntity> getAllUsers () {
        return userService.getAllUsers();
    }
}
