package com.nod.lone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignupRequest {
    @Column(unique = true)
    private String email;
    private String username;
    private String password;

}
