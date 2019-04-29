package cn.whuerbbs.backend.enumeration;

public enum NotificationType implements BaseEnum {
    POST_COMMENTED(1),
    POST_LIKED(2),
    COMMENT_LIKED(3);

    private final int type;

    NotificationType(int type) {
        this.type = type;
    }

    @Override
    public int value() {
        return type;
    }
}
