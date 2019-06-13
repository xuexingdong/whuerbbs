package cn.whuerbbs.backend.service;

import cn.whuerbbs.backend.common.RedisKeys;
import cn.whuerbbs.backend.config.JwtConfig;
import cn.whuerbbs.backend.model.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public Token refreshToken(String userId) {
        var token = new Token(generateToken(userId, Map.of("userId", userId)), jwtConfig.getExpiration().getSeconds());
        stringRedisTemplate.opsForValue().set(String.format(RedisKeys.USER, userId), userId, token.getExpiration(), TimeUnit.SECONDS);
        return token;
    }

    public Optional<Claims> parseToken(String token) {
        var encodedKey = Base64.getEncoder().encode(jwtConfig.getSecretKey().getBytes());
        var key = Keys.hmacShaKeyFor(encodedKey);
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token).getBody();
        } catch (JwtException e) {
            logger.error("JWT exception: {}", e.getMessage());
        }
        return Optional.ofNullable(claims);
    }

    public void removeToken(String token) {
        Optional<Claims> claimsOptional = parseToken(token);
        claimsOptional.ifPresent(claim -> stringRedisTemplate.delete(String.format(RedisKeys.USER, claim.get("userId"))));
    }

    private String generateToken(String subject, Map<String, Object> claim) {
        var encodedKey = Base64.getEncoder().encode(jwtConfig.getSecretKey().getBytes());
        var key = Keys.hmacShaKeyFor(encodedKey);
        return Jwts.builder().addClaims(claim).setSubject(subject).signWith(key).compact();
    }

}
