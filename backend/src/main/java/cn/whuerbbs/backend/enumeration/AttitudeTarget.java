package cn.whuerbbs.backend.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum AttitudeTarget implements BaseEnum {
    POST(1),
    COMMENT(2);

    private final int target;

    AttitudeTarget(int target) {
        this.target = target;
    }

    @Override
    public int value() {
        return target;
    }

    @JsonCreator
    public static AttitudeTarget fromValue(int value) {
        for (AttitudeTarget attitudeTarget : values()) {
            if (attitudeTarget.value() == value) {
                return attitudeTarget;
            }
        }
        return null;
    }
}
