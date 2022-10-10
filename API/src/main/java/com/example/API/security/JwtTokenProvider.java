package com.example.API.security;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {
	private final String JWT_SECRET = "hieune99";
	private final long JWT_EXPIRATION = 604800000L;
	
	// Tạo ra jwt từ thông tin user
	// Tạo ra jwt từ thông tin user
    public String generateToken(CustomUserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        // Tạo chuỗi json web token từ sdt của user.
        return Jwts.builder()
                   .setSubject(userDetails.getUser().getSdt())
                   .setIssuedAt(now)
                   .setExpiration(expiryDate)
                   .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                   .compact();
    }

    // Lấy thông tin user từ jwt
    public String getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                            .setSigningKey(JWT_SECRET)
                            .parseClaimsJws(token)
                            .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
        	ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tài khoản không hợp lệ"); 
        } catch (ExpiredJwtException ex) {
            ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Phiên đăng nhập đã hết hạn"); 
        } catch (UnsupportedJwtException ex) {
            ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Tài khản không được hỗ trợ"); 
        } catch (IllegalArgumentException ex) {
        	ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tài khoản trống"); 
        }
        return false;
    }
}
