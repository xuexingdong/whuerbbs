package cn.whuerbbs.backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.Duration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

    @NotEmpty
    private String secretKey;

    @Min(0)
    private Duration expiration;

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Duration getExpiration() {
        return expiration;
    }

    public void setExpiration(Duration expiration) {
        this.expiration = expiration;
    }
}
