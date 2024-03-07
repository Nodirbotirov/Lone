package com.nod.lone.service;

import com.nod.lone.dto.UserDto;
import com.nod.lone.model.User;
import com.nod.lone.dto.LoginRequest;
import com.nod.lone.dto.SignupRequest;
import org.springframework.http.HttpEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.text.ParseException;


public interface UserService extends UserDetailsService {

    HttpEntity<?> findAllUsers();

    HttpEntity<?> identification(UserDto user) throws ParseException;

    HttpEntity<?> findByEmail(String email);

    HttpEntity<?> updateUser(UserDto userDto);

    HttpEntity<?> deleteUser(Long id);

    HttpEntity<?> signIn(LoginRequest loginRequest);

    User loadByUserId(Long id);

    UserDetails loadUserByUsername(String username);

    HttpEntity<?> signUp(SignupRequest signupRequest);

    HttpEntity<?> findById(Long id);

    HttpEntity<?> userInfo(Long id);
}
