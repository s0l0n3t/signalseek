package com.furkantokgoz.security.jwt;

import com.furkantokgoz.dto.AdminUserDto;
import com.furkantokgoz.dto.RoomDto;
import com.furkantokgoz.dto.UserDto;
import com.furkantokgoz.exception.TokenNotFoundException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.text.DateFormat;
import java.time.*;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtUtil implements Serializable {

    // hash işlemi yaparken kullanılacak key
    private SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private final int expireIn = 24*60 * 60 * 1000; //1 day expires


    // verilen token a ait kullanıcı adını döndürür.
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    public Collection<? extends GrantedAuthority> extractAuthorities(String token) {
        Claims claims = extractAllClaims(token);
        var roles = (Collection<?>) claims.get("roles");
        return roles.stream()
                .map(role -> (SimpleGrantedAuthority) new SimpleGrantedAuthority(role.toString()))
                .toList();
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
    public Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(SECRET_KEY)
                .build()
                .parseClaimsJws(token).getPayload();
    }
    // token ın geçerlilik süre doldu mu?
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // userDetails objesini alır. createToken metoduna gönderir.
    public JwtResponse generateToken(String hash, Collection<? extends GrantedAuthority> authorities) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", authorities.stream().map(GrantedAuthority::getAuthority).toList());
        return JwtResponse.builder()
                .key(hash)
                .token(createToken(claims,hash))
                .expiresIn(LocalDateTime.now().withNano(0).plus(Duration.ofMillis(expireIn)).atZone(ZoneId.systemDefault()))
                .build();
    }

    private String createToken(Map<String, Object> claims, String hash) {
        return Jwts.builder().setClaims(claims)
                .setSubject(hash) // ilgili kullanıcı
                .setIssuedAt(new Date(System.currentTimeMillis())) // başlangıç
                .setExpiration(new Date(System.currentTimeMillis() + expireIn)) // bitiş
                .signWith(SECRET_KEY,Jwts.SIG.HS256) // kullanılan algoritma ve bu algoritma çalışırken kullanılacak hash key değeri
                .compact();
    }

    // token hala geçerli mi? kullanıcı adı doğru ise ve token ın geçerlilik süresi devam ediyorsa true döner.
    public Boolean validateToken(String token) {
        try{
            return !isTokenExpired(token);
        }catch (JwtException e){
            return false;
        }
    }
}
