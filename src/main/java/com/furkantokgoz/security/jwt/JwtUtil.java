package com.furkantokgoz.security.jwt;

import com.furkantokgoz.dto.AdminUserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil implements Serializable {

    // hash işlemi yaparken kullanılacak key
    private SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private final int expireIn = 30 * 60 * 1000;

    // verilen token a ait kullanıcı adını döndürür.
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // verilen token a ait token bitiş süresini verir.
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // verilen token a ait claims bilgisini alır.
    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(SECRET_KEY)
                .build()
                .parseClaimsJws(token).getPayload();
    }

    // token ın geçerlilik süre doldu mu?
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // userDetails objesini alır. createToken metoduna gönderir.
    public JwtResponse generateToken(AdminUserDto adminUserDto) {
        Map<String, Object> claims = new HashMap<>();
        return JwtResponse.builder()
                .token(createToken(claims,adminUserDto.getUsername()))
                .expiresIn(new Date(System.currentTimeMillis() + expireIn)) //GMT +0
                .build();
    }

    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder().setClaims(claims)
                .setSubject(username) // ilgili kullanıcı
                .setIssuedAt(new Date(System.currentTimeMillis())) // başlangıç
                .setExpiration(new Date(System.currentTimeMillis() + expireIn)) // bitiş
                .signWith(SECRET_KEY,Jwts.SIG.HS256) // kullanılan algoritma ve bu algoritma çalışırken kullanılacak hash key değeri
                .compact();
    }

    // token hala geçerli mi? kullanıcı adı doğru ise ve token ın geçerlilik süresi devam ediyorsa true döner.
    public Boolean validateToken(String token, AdminUserDto adminUserDto) {
        final String username = extractUsername(token);
        return (username.equals(adminUserDto.getUsername()) && !isTokenExpired(token));
    }
}
