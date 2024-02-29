package com.nod.lone.controller;

import com.nod.lone.model.User;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.nod.lone.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    public List<User> findAllUsers() {

        //todo
        return service.findAllUsers();
    }

    @PostMapping("save_user")
    public String saveUser(@RequestBody User user) {
        service.saveUser(user);
        return "User successfully saved";
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
