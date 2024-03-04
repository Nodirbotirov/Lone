package com.nod.lone.controller;

import com.nod.lone.model.CurrentUser;
import com.nod.lone.model.User;
import com.nod.lone.payload.LoginRequest;
import com.nod.lone.payload.SignupRequest;
import com.nod.lone.repository.UserRepository;
import com.nod.lone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;


    @PostMapping("/signin")
    public HttpEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return userService.signIn(loginRequest);
    }
    @PostMapping("/signup")
    public HttpEntity<?> signup(@RequestBody SignupRequest signupRequest){
        return userService.signUp(signupRequest);
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
