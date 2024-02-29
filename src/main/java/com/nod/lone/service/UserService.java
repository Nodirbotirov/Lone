package com.nod.lone.service;

import com.nod.lone.model.User;


import java.util.List;


public interface UserService {

    List<User> findAllUsers();
    User saveUser(User user);

    User findByEmail(String email);

    User updateUser(User user);

    void deleteUser(String email);

}
