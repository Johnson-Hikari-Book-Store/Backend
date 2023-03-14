package com.bookhub.bookhub_backend.user;

import com.bookhub.bookhub_backend.user.dto.UpdateUserDto;
import com.bookhub.bookhub_backend.user.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        Map<String, Object> response = new HashMap<>();
        List<UserEntity> data = userService.getAllUsers();
        response.put("success", true);
        response.put("message", "users fetched successfully");
        response.put("data", data);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> getSingleUser(@PathVariable String userId) {
        Map<String, Object> response = new HashMap<>();
        UserEntity data = userService.getSingleUser(userId);
        response.put("success", true);
        response.put("message", "user fetched successfully");
        response.put("data", data);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> updateUser(
            @PathVariable String userId,
            @RequestBody UpdateUserDto updateUserDto
    ) {
        Map<String, Object> response = new HashMap<>();
        UserEntity data = userService.updateSingleUser(userId, updateUserDto);
        response.put("success", true);
        response.put("message", "user updated successfully");
        response.put("data", data);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable String userId) {
        Map<String, Object> response = new HashMap<>();
        userService.deleteSingleUser(userId);
        response.put("success", true);
        response.put("message", "user deleted successfully");
        return ResponseEntity.ok(response);
    }
}
