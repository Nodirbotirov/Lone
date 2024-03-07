package com.nod.lone.controller;

import com.nod.lone.dto.UserDto;
import com.nod.lone.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService service;

    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN','ADMIN')")
    @GetMapping("/all")
    public HttpEntity<?> findAllUsers() {
        return service.findAllUsers();
    }

    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN','ADMIN')")
    @PostMapping(value = "save_user", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public HttpEntity<?> saveUser(@ModelAttribute UserDto userDto) throws ParseException {
        return service.identification(userDto);
    }

    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN','ADMIN')")
    @GetMapping("by_id/{id}")
    public HttpEntity<?> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN','ADMIN')")
    @GetMapping("by_email/{email}")
    public HttpEntity<?> findByEmail(@PathVariable String email) {
        return service.findByEmail(email);
    }


    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN','ADMIN')")
    @PutMapping("update_user")
    public HttpEntity<?> updateUser(@RequestBody UserDto userDto) {
        return service.updateUser(userDto);
    }


    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN','ADMIN')")
    @DeleteMapping("delete_mapping/{id}")
    public HttpEntity<?> deleteUser(@PathVariable Long id) {
        return service.deleteUser(id);
    }

    @GetMapping("/user-info/{id}")
    public HttpEntity<?> userInfo(@PathVariable Long id){
        return service.userInfo(id);
    }

}
