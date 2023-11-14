// package com.example.DATN_API.Service;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Service;

// import java.util.Date;

// @Service
// public class JwtTokenService {

//     @Value("${jwt.secret}")
//     private String secretKey;

//     @Value("${jwt.expiration}")
//     private Long expirationTime;

//     public String generateToken(String username) {
//         return Jwts.builder()
//                 .setSubject(username)
//                 .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
//                 .signWith(SignatureAlgorithm.HS512, secretKey)
//                 .compact();
//     }
// }

