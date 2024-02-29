//package com.nod.lone.repository;
//
//import com.nod.lone.model.User;
//import org.springframework.stereotype.Repository;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.IntStream;
//
//
//@Repository
//public class InMemoryUserDAO {
//    private final List<User> Users = new ArrayList<>();
//
//
//    public List<User> findAllUsers() {
//        return Users;
//    }
//
//
//    public User saveUser(User user) {
//        Users.add(user);
//        return user;
//    }
//
//
//    public User findByEmail(String email) {
//        return Users.stream()
//                .filter(element -> element.getEmail().equals(email))
//                .findFirst()
//                .orElse(null);
//    }
//
//
//    public User updateUser(User user) {
//        var studentIndex = IntStream.range(0, Users.size())
//                .filter(index -> Users.get(index).getEmail().equals(user.getEmail()))
//                .findFirst()
//                .orElse(-1);
//        if (studentIndex > -1) {
//            Users.set(studentIndex, user);
//            return user;
//        }
//        return null;
//    }
//
//
//    public void deleteUser(String email) {
//        var student = findByEmail(email);
//        if (student != null) {
//            Users.remove(student);
//        }
//
//    }
//}
