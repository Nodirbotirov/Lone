package com.nod.lone.service.impl;

import com.nod.lone.model.RoleName;
import com.nod.lone.model.User;
import com.nod.lone.payload.LoginRequest;
import com.nod.lone.payload.SignupRequest;
import com.nod.lone.payload.TokenPayload;
import com.nod.lone.repository.UserRepository;
import com.nod.lone.securityConfiguration.JwtTokenProvider;
import com.nod.lone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository repository;

    @Autowired
     AuthenticationManager authenticationManager;

    @Autowired
     JwtTokenProvider jwtTokenProvider;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public List<User> findAllUsers() {
        return repository.findAllByRoleNotIn(List.of(RoleName.SUPER_ADMIN));
    }

    @Override
    public User saveUser(User user) {
        return repository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return repository.findUserByEmail(email);
    }

    @Override
    public User updateUser(User user) {
        return repository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(String email) {
        repository.deleteByEmail(email);
    }

    @Override
    public ResponseEntity<?> signIn(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User principal = (User) authentication.getPrincipal();
            TokenPayload jwt = jwtTokenProvider.generateToken(principal);
            System.out.println("successfully");
            return ResponseEntity.ok(jwt);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(500);
        }
    }

    @Override
    public ResponseEntity<?> signUp(SignupRequest signupRequest) {
        if (repository.existsUserByUsername(signupRequest.getUsername())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Choose different name");
        }
        if (repository.existsUsersByEmail(signupRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("choose different email");
        }

        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setPassword(signupRequest.getPassword());
        user.setEmail(signupRequest.getEmail());
        user.setFirstName(signupRequest.getFirstName());
        user.setLastName(signupRequest.getLastName());
        user.setAge(signupRequest.getAge());
        user.setRole(signupRequest.getRole());
        user.setDateOfBirth(signupRequest.getDateOfBirth());


        repository.save(user);
        return ResponseEntity.ok("Success baby");
    }

    public User loadByUserId(Long id) {
        try {
            Optional<User> byId = repository.findById(id);
            return byId.orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Optional<User> byUsername = repository.findByUsername(username);
            if (byUsername.isPresent()) {
                return byUsername.get();
            } else {
                throw new UsernameNotFoundException("Username or password incorrect!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new UsernameNotFoundException("Error enter with login and password!");
        }
    }


}
