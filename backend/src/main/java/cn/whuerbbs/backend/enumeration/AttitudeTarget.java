package cn.whuerbbs.backend.enumeration;

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
}
