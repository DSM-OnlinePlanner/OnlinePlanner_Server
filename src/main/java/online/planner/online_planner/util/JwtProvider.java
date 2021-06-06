package online.planner.online_planner.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    @Value("${jwt.access.token}")
    private long accessExpire;

    @Value("${jwt.refresh.token}")
    private long refreshExpire;

    @Value("${jwt.secret.key}")
    private String secretKey;

    public String generateAccessToken(String email) {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessExpire * 1000))
                .setSubject(email)
                .claim("type", "access_token")
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String generateRefreshToken(String email) {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpire * 1000))
                .setSubject(email)
                .claim("type", "refresh_token")
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                    .getBody().getSubject();

            return true;
        }catch (Exception e) {
            return false;
        }
    }

    public boolean isRefreshToken(String token) {
        if(validateToken(token))
            throw new RuntimeException();

        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("type").toString().equals("refresh_token");
    }

    public String getEmail(String token) {
        if(validateToken(token))
            throw new RuntimeException();

        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().getSubject();
    }
}
