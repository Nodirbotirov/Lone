package com.nod.lone.model;

import lombok.*;

import java.time.LocalDate;

//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@RequiredArgsConstructor
@Data
@Builder
public class Student {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    @NonNull
    private String email;
    private int age;

}
