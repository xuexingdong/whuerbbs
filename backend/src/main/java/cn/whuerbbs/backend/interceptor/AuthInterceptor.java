package cn.whuerbbs.backend.interceptor;

import cn.whuerbbs.backend.common.CurrentUserData;
import cn.whuerbbs.backend.common.RedisKeys;
import cn.whuerbbs.backend.service.JwtService;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    @Autowired
    private JwtService jwtService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // ignore option request
        if (HttpMethod.OPTIONS.name().equals(request.getMethod())) {
            return true;
        }
        var authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isNotEmpty(authorization)) {
            if (authorization.startsWith("Bearer ")) {
                var token = authorization.substring(7);
                var claimsOptional = jwtService.parseToken(token);
                if (claimsOptional.isPresent()) {
                    var userId = claimsOptional.get().getSubject();
                    if (BooleanUtils.isTrue(stringRedisTemplate.hasKey(String.format(RedisKeys.USER, userId)))) {
                        CurrentUserData currentUserData = new CurrentUserData();
                        currentUserData.setUserId(userId);
                        request.setAttribute("currentUser", currentUserData);
                        return true;
                    }
                }
            }
        }
        logger.warn("Wrong authorization: {}", authorization);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return false;
    }
}