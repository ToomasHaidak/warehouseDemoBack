package com.example.warehousedemoback.service;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LoginService {

    public String login(String userName) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 1000l*60l*60l*24l*10l);
        JwtBuilder jwtBuilder = Jwts.builder()
                .setExpiration(expiration)
                .setIssuedAt(now)
                .setIssuer("issuer")
                .signWith(SignatureAlgorithm.HS256, "secret")
                .claim("username", userName);
        String jwt = jwtBuilder.compact();
        return jwt;
    }
}
