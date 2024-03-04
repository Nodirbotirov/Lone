package com.nod.lone.controller;

import com.nod.lone.dto.UserDto;
import com.nod.lone.model.User;
import com.nod.lone.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService service;

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @GetMapping("/all")
    public List<User> findAllUsers() {

        //todo
        return service.findAllUsers();
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @PostMapping("save_user")
    public HttpEntity<?> saveUser(@RequestBody UserDto userDto) {
        return service.identification(userDto);
    }

    @GetMapping("/{email}")
    public User findByEmail(@PathVariable String email) {
        return service.findByEmail(email);
    }


    @PutMapping("update_user")
    public User updateUser(@RequestBody User user) {
        return service.updateUser(user);
    }


    @DeleteMapping("delete_mapping/{email}")
    public void deleteUser(@PathVariable String email) {
        service.deleteUser(email);
    }


}
