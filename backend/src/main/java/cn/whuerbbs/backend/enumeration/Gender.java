package cn.whuerbbs.backend.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Gender implements BaseEnum {
    UNKNOWN(0),
    MALE(1),
    FEMALE(2);

    private final int gender;

    Gender(int gender) {
        this.gender = gender;
    }

    @Override
    public int value() {
        return gender;
    }

    @JsonCreator
    public static Gender fromValue(int value) {
        for (Gender gender : values()) {
            if (gender.value() == value)
                return gender;
        }
        return null;
    }
}
