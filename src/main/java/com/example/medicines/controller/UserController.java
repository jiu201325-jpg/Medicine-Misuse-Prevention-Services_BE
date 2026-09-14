package com.example.medicines.controller;

import com.example.medicines.dto.ApiResponse;
import com.example.medicines.entity.UserType;
import com.example.medicines.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ApiResponse<?> register(@RequestParam String phoneNumber, @RequestParam UserType userType) {
        return ApiResponse.success(userService.register(phoneNumber, userType));
    }
}
