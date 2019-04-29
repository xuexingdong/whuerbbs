package cn.whuerbbs.backend.enumeration;

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
}
