package com.nod.lone.component;


import com.nod.lone.model.enums.RoleName;
import com.nod.lone.model.User;
import com.nod.lone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;


    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
        try {
            Optional<User> byUsername = userRepository.findByUsername("admin123");
            if (byUsername.isEmpty()) {
                userRepository.save(new User(
                        "admin123",
                        passwordEncoder.encode("admin123"),
                        RoleName.SUPER_ADMIN
                ));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
//        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
