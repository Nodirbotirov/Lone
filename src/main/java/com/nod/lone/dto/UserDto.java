package com.nod.lone.dto;

import com.nod.lone.model.BankCard;
import com.nod.lone.model.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import java.util.List;

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
    private String phoneNumber;
    private String password;
    private RoleName role;
    private MultipartFile photo;
    private List<BankCard> cards;

    public UserDto(Long id, String firstName, String lastName, String dateOfBirth, String email, List<BankCard> cards) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.cards = cards;
    }
}
