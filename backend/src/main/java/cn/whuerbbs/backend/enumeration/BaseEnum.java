package cn.whuerbbs.backend.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

public interface BaseEnum {
    @JsonValue
    int value();

    static <T extends BaseEnum> T fromValue(Class<T> enumType, int value) {
        for (T object : enumType.getEnumConstants()) {
            if (Objects.equals(value, object.value())) {
                return object;
            }
        }
        return null;
    }
}