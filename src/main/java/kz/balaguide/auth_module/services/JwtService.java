package kz.balaguide.auth_module.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import kz.balaguide.auth_module.dtos.JwtResponseDto;
import kz.balaguide.common_module.core.entities.AuthUser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {

    @Value("${token.signing.key}")
    private String jwtSigningKey;

    @Value("${token.lifetime}")
    @Getter
    private Long tokeLifetimeMillis;

    private final RedisService redisService;

    /**
     * Extracting the username from the token
     *
     * @param token token
     * @return username
     */
    public String extractPhoneNumber(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Token generation
     *
     * @return token
     */
    public JwtResponseDto generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        if (userDetails instanceof AuthUser customUserDetails) {
            claims.put("id", customUserDetails.getId());
            claims.put("phoneNumber", customUserDetails.getPhoneNumber());
            claims.put("role", customUserDetails.getRole());
        }

        JwtResponseDto tokenDto = generateToken(claims, userDetails);
        redisService.saveToken(userDetails.getUsername(), tokenDto.getToken(), tokeLifetimeMillis);
        return tokenDto;
    }

    /**
     * Check token validation
     *
     * @param token       token
     * @param userDetails data of users
     * @return true, if token is valid
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String phoneNumber = extractPhoneNumber(token);
        boolean notExpired = !isTokenExpired(token);
        boolean matchesRedis = redisService.isTokenValid(phoneNumber, token);
        return phoneNumber.equals(userDetails.getUsername()) && notExpired && matchesRedis;
    }

    /**
     * Extracting data from the token
     *
     * @param token           token
     * @param claimsResolvers data extraction function
     * @param <T>             data type
     * @return data
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    /**
     * Token generation
     *
     * @param extraClaims extra data
     * @param userDetails user data
     * @return token
     */
    private JwtResponseDto generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return JwtResponseDto.builder()
                .token(
                        Jwts.builder()
                                .claims()
                                .add(extraClaims)
                                .subject(userDetails.getUsername())
                                .issuedAt(new Date(System.currentTimeMillis()))
                                .expiration(new Date(System.currentTimeMillis() + tokeLifetimeMillis))
                                .and()
                                .signWith(getSigningKey(), Jwts.SIG.HS256)
                                .compact()
                )
                .createdAt(new Date(System.currentTimeMillis()))
                .expiresAt(new Date(System.currentTimeMillis() + tokeLifetimeMillis))
                .build();
    }

    /**
     * Check token for expiration
     *
     * @param token token
     * @return true if the token is expired
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date(System.currentTimeMillis()));
    }

    /**
     * Retrieve token expiration date
     *
     * @param token token
     * @return expiration date
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extract all data from the token
     *
     * @param token token
     * @return data
     */
    private Claims extractAllClaims(String token) {
        SecretKey secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSigningKey));
        return Jwts.parser()
                .verifyWith(secret)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Obtaining a key for token signing
     *
     * @return key
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
