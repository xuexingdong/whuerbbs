package cn.whuerbbs.backend.global;

import cn.whuerbbs.backend.exception.BaseException;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

@RestControllerAdvice
public class GlobalResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (Objects.isNull(body)) {
            return Resp.ok();
        }
        if (body instanceof Page) {
            response.getHeaders().set("X-TOTAL-COUNT", String.valueOf(((Page) body).getTotalElements()));
            return Resp.data(((Page) body).getContent());
        }
        if (body instanceof BaseException) {
            return Resp.error(((BaseException) body).getMessage());
        }
        return Resp.data(body);
    }
}
