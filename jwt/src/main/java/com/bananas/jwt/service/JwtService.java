package com.bananas.jwt.service;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * Servicio que gestiona el jwt
 */
@Service
public class JwtService {

    /**
     * Inyectamos llave secreta
     */
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    /**
     * Inyectamos expiración del token
     */
    @Value("${security.jwt.token-expiration}")
    private Long tokenExpiration;

    /**
     * Transforma la clave secreta en una firma en formato (BASE64)
     * y lo convierte a una firma HMAC()
     * 
     * @return SecretKey (firma secreta)
     */
    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Método para genera un jwt a un usuario
     * 
     * @param email
     * @param userId
     * @param rolId
     * @return String jwt
     */
    public String generateToken(String email, Long userId, Long rolId) {
        return Jwts.builder()
                .subject(email) // A quien pertenece el token (jwt)
                .claims(Map.of("userId", userId)) // Claims personalizados
                .claims(Map.of("rolId", rolId)) // Claims personalizados
                .issuedAt(new Date()) // Fecha de creación del jwt
                .expiration(new Date(System.currentTimeMillis() + tokenExpiration)) // Fecha de expiración del jwt
                                                                                    // expresada en ms
                .signWith(getSignKey()) // Firmado con (método de generar firma con clave secreta)
                .compact(); // Unimos todos los datos para construir el payload
    }

    /**
     * Método que valida si un token pertenece a mi app, si las firmas y los claims
     * coinciden
     * 
     * @param token
     * @return Boolean, si el token es valido (true) sino (false)
     */
    public Boolean isTokenValid(String token) {
        try {
            Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Método para extraer todos los claims
     * 
     * @param <T>
     * @param token
     * @param resolver
     * @return todos los claims
     */
    public <T> T extractClaims(String token, Function<Claims, T> resolver) {
        final Claims claims = Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return resolver.apply(claims);
    }

    /**
     * Método para extraer el propietario del token (subject)
     * 
     * @param token
     * @return subject
     */
    public String extractSubject(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    /**
     * Extraer el id del usuario (claim personalizado)
     * 
     * @param token
     * @return id del usuario
     */
    public Long extractUserId(String token) {
        return extractClaims(token, claims -> claims.get("userId", Long.class));
        // Castear
    }

    /**
     * Extraer el id del rol del usuario (claim personalizado)
     * 
     * @param token
     * @return id del rol del usuario
     */
    public Long extractRolId(String token) {
        return extractClaims(token, claims -> claims.get("rolId", Long.class));
        // Castear
    }

    /**
     * Método para el refresco del token
     * 
     * @param token viejo
     * @return token nuevo
     * @throws Exception
     */
    public String refreshToken(String token) throws Exception {
        Claims claims;

        try {
            claims = Jwts.parser()
                    .verifyWith(getSignKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            throw new Exception("Token is expired", e);
        } catch (JwtException e) {
            e.printStackTrace();
            throw new Exception("Token is invalid", e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Token is invalid", e);
        }

        return generateToken(claims.getSubject(), claims.get("userId", Long.class), claims.get("rolId", Long.class));
    }
}
