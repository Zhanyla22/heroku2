package com.example.SpringZhanyl.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

//1
@Component
public class JwtTokenProvider {
    private final UserDetailsService userDetailsService;

    //квалифаер когда доступно больше одного бина компонента одного типа
    public JwtTokenProvider(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    //внутри эппликейшн пропертис

    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.expiration}")
    private long validityInMseconds;
    @Value("${jwt.header}")
    private String authorizationHeader;

    //зашифруем ключ
    @PostConstruct
    protected  void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }
    //создаем токен
    public String createToken(String username,String role){
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role",role);
        Date now = new Date();
        Date validity = new Date(now.getTime()+validityInMseconds*1000);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();
    }

    //валидация токена
    public boolean validateToken(String token){
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        }
        catch (JwtException | IllegalArgumentException e){
            throw new JwtAuthenticationException("JWT token is expired or invalid", HttpStatus.UNAUTHORIZED);
        }
    }

    //получить аутентификацию
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUserName(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserName(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
    // для контроллера
    public String resolveToken(HttpServletRequest request){
        return request.getHeader(authorizationHeader);
    }
}
