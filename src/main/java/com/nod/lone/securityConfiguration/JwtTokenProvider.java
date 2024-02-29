package com.nod.lone.securityConfiguration;

import com.nod.lone.model.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwtSecret}")
    private String secretKey;

    @Value("${token.lifecycle.millisecond}")
    private long tokenExpireTime;

    public TokenPayload generateToken(User user) {
        Date date = new Date();
        long l = date.getTime() + tokenExpireTime;
        Date expireDate = new Date(l);

        return new TokenPayload(expireDate,Jwts
                .builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(date)
                .claim("roles", user.getRole())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.ES256, secretKey)
                .compact());
    }

    public boolean validateToken(String token) {
        try {
            Jwts
                    .parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.err.println("Muddati o'tgan");
        } catch (MalformedJwtException malformedJwtException) {
            System.err.println("Buzilgan token");
        } catch (SignatureException s) {
            System.err.println("Kalit so'z xato");
        } catch (UnsupportedJwtException unsupportedJwtException) {
            System.err.println("Qo'llanilmagan token");
        } catch (IllegalArgumentException ex) {
            System.err.println("Bo'sh token");
        }
        return false;
    }

    public String getUserIdFromToken(String token) {
        return Jwts
                .parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
