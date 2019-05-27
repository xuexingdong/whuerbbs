package cn.whuerbbs.backend.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum NotificationType implements BaseEnum {
    POST_COMMENTED(1),
    POST_LIKED(2),
    COMMENT_LIKED(3),
    COMMENT_REPLIED(4),
    SUB_COMMENT_LIKED(5);

    private final int type;

    NotificationType(int type) {
        this.type = type;
    }

    @Override
    public int value() {
        return type;
    }

    @JsonCreator
    public static NotificationType fromValue(int value) {
        for (NotificationType notificationType : values()) {
            if (notificationType.value() == value) {
                return notificationType;
            }
        }
        return null;
    }
}
