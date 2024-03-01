package com.nod.lone.repository;

import com.nod.lone.model.RoleName;
import com.nod.lone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    void deleteByEmail(String email);
    User findUserByEmail(String email);
    Optional<User> findByUsername(String username);
    Boolean existsUserByUsername(String username);
    Boolean existsUsersByEmail(String email);

    List<User> findAllByRoleNotIn(List<RoleName> roleNames);

}
