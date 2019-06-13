package cn.whuerbbs.backend.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Campus implements BaseEnum {
    QUANXIAOQU(1, "全校区"),
    WENLI(2, "文理学部"),
    XINXIXUEBU(3, "信息学部"),
    GONGXUEBU(4, "工学部"),
    YIXUEBU(5, "医学部"),
    XIANSHANG(6, "线上");

    private final int value;
    private final String title;

    Campus(int value, String title) {
        this.value = value;
        this.title = title;
    }

    @Override
    public int value() {
        return value;
    }

    @JsonCreator
    public static Campus fromValue(int value) {
        for (Campus campus : values()) {
            if (campus.value == value) {
                return campus;
            }
        }
        return null;
    }

    public String getTitle() {
        return title;
    }
}
