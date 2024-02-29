package com.nod.lone.service;

import com.nod.lone.model.User;
import com.nod.lone.securityConfiguration.LoginRequest;
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
}
