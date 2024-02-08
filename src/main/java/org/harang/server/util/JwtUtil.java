package org.harang.server.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.harang.server.constants.jwt.JwtProperties;
import org.harang.server.domain.enums.Type;
import org.harang.server.dto.response.JwtTokenResponse;
import org.harang.server.dto.type.ErrorMessage;
import org.harang.server.exception.CustomException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtUtil implements InitializingBean {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.access-token-expire-period}")
    private Integer accessTokenExpirePeriod;

    @Value("${jwt.refresh-token-expire-period}")
    private Integer refreshTokenExpirePeriod;

    private Key key;


    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public JwtTokenResponse generateTokens(Long id, Type type) {
        return JwtTokenResponse.of(
                generateAccessToken(id, type),
                generateRefreshToken(id)
        );
    }

    public boolean verifyToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return claims.getBody()
                    .getExpiration()
                    .after(new Date(System.currentTimeMillis()));
        }catch (Exception e) {
            return false;
        }
    }

    public Claims getClaim(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    // JWS - 서버의 PK로 인증정보를 서명한 것
                    .parseClaimsJws(token)
                    .getBody();
        }
        catch (MalformedJwtException e) {
            throw new CustomException(ErrorMessage.INVALID_JWT);
        }catch (ExpiredJwtException e) {
            throw new CustomException(ErrorMessage.EXPIRED_JWT);
        }catch (UnsupportedJwtException e) {
            throw new CustomException(ErrorMessage.UNSUPPORTED_JWT);
        }catch (IllegalArgumentException e) {
            throw new CustomException(ErrorMessage.JWT_IS_EMPTY);
        }
    }

    private String generateAccessToken(Long id, Type type) {
        Claims claims = Jwts.claims();
        claims.put(JwtProperties.MEMBER_ID_CLAIM_NAME, id);
        claims.put(JwtProperties.MEMBER_ROLE_CLAIM_NAME, type);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpirePeriod))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    private String generateRefreshToken(Long id) {
        Claims claims = Jwts.claims();
        claims.put(JwtProperties.MEMBER_ID_CLAIM_NAME, id);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpirePeriod))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }
}
