package com.nod.lone.service;

import com.nod.lone.model.User;
import com.nod.lone.payload.LoginRequest;
import com.nod.lone.payload.SignupRequest;
import org.springframework.http.HttpEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface UserService extends UserDetailsService {

    List<User> findAllUsers();

    User saveUser(User user);

    User findByEmail(String email);

    User updateUser(User user);

    void deleteUser(String email);

    HttpEntity<?> signIn(LoginRequest loginRequest);

    User loadByUserId(Long id);

    UserDetails loadUserByUsername(String username);

    HttpEntity<?> signUp(SignupRequest signupRequest);
}
