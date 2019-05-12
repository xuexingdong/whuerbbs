package cn.whuerbbs.backend.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Board implements BaseEnum {
    NEWS(1, "新鲜事"),
    SECONDHAND(2, "二手区"),
    ASK_THINGS(3, "问点事"),
    ANONYMOUS_POST(4, "匿名区"),
    PLAY_TOGETHER(5, "一起玩");

    private final int value;
    private final String title;

    Board(int value, String title) {
        this.value = value;
        this.title = title;
    }

    @Override
    public int value() {
        return value;
    }

    @JsonCreator
    public static Board fromValue(int value) {
        for (Board board : values()) {
            if (board.value == value) {
                return board;
            }
        }
        return null;
    }

    public String getTitle() {
        return title;
    }
}
