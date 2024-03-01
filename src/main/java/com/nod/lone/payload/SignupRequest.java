package com.nod.lone.payload;

import com.nod.lone.model.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignupRequest {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    @Column(unique = true)
    private String email;
    private int age;
    private String username;
    private String password;
    private RoleName role;
}
