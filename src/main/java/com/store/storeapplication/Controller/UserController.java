package com.store.storeapplication.Controller;

import com.store.storeapplication.Entity.User;
import com.store.storeapplication.Repository.UserRepository;
import com.store.storeapplication.Comman.BusinessCode;
import com.store.storeapplication.DTO.ApiResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // GET all users
    @GetMapping
    public ApiResponse<List<User>> getAllUsers() {
        return new ApiResponse<>(
                true,
                "Users fetched successfully", BusinessCode.SUCCESS,
                userRepository.findAll());
    }

    // POST register user (simple)
    @PostMapping
    public ResponseEntity<ApiResponse<User>> createUser(@RequestBody User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "User name cannot be empty", BusinessCode.VALIDATION_ERROR, null));
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "User email cannot be empty", BusinessCode.VALIDATION_ERROR, null));
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>(false, "User password cannot be empty", BusinessCode.VALIDATION_ERROR, null));
        }
        User savedUser = userRepository.save(user);
        return ResponseEntity
                .ok(new ApiResponse<>(true, "User registered successfully", BusinessCode.SUCCESS, savedUser));
    }
}
