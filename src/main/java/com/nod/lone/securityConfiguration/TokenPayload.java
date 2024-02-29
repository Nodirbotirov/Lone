package com.nod.lone.securityConfiguration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TokenPayload {
    private Date expireDate;

    private String token;
}