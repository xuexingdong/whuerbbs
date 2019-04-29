package cn.whuerbbs.backend.enumeration;

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
}
