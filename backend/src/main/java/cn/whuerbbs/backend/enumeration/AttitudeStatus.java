package cn.whuerbbs.backend.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum AttitudeStatus implements BaseEnum {
    DEFAULT(0),
    LIKE(1);

    private final int status;

    AttitudeStatus(int status) {
        this.status = status;
    }

    @Override
    public int value() {
        return status;
    }

    @JsonCreator
    public static AttitudeStatus fromValue(int value) {
        for (AttitudeStatus attitudeStatus : values()) {
            if (attitudeStatus.value() == value) {
                return attitudeStatus;
            }
        }
        return null;
    }
}
