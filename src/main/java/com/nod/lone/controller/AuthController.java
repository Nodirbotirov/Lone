package com.nod.lone.controller;

import com.nod.lone.model.CurrentUser;
import com.nod.lone.model.User;
import com.nod.lone.securityConfiguration.LoginRequest;
import com.nod.lone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return userService.signIn(loginRequest);
    }

    @GetMapping("/me")
    public HttpEntity<?> getMe(@CurrentUser User user) {
        if (user!=null){
            return ResponseEntity.ok(Map.of(
                    "username", user.getUsername(),
                    "role", user.getRole(),
                    "id", user.getId()
            ));
        }else {
            return ResponseEntity.ok(403);
        }
    }



}
