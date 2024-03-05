package com.nod.lone.dto;

import com.nod.lone.model.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    @Column(unique = true)
    private String email;
    private String username;
    private String password;
    private RoleName role;
    private MultipartFile photo;

}
