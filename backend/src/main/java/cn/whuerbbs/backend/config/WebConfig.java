package cn.whuerbbs.backend.config;

import cn.whuerbbs.backend.common.CurrentUserMethodArgumentResolver;
import cn.whuerbbs.backend.converter.IntegerToBaseEnumConverterFactory;
import cn.whuerbbs.backend.converter.StringToBaseEnumConverterFactory;
import cn.whuerbbs.backend.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .excludePathPatterns("/wxlogin");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new CurrentUserMethodArgumentResolver());
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new IntegerToBaseEnumConverterFactory());
        registry.addConverterFactory(new StringToBaseEnumConverterFactory());
    }
}
