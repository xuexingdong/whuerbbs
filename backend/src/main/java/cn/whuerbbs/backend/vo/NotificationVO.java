package cn.whuerbbs.backend.vo;

import cn.whuerbbs.backend.enumeration.Board;
import cn.whuerbbs.backend.enumeration.NotificationType;
import cn.whuerbbs.backend.model.Notification;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

public class NotificationVO {
    private long id;
    private NotificationType type;
    private String referenceId;
    private Board board;
    private String content;
    private String summary;
    private UserVO fromUser;
    private LocalDateTime createdAt;

    public NotificationVO(Notification notification, Board board, String summary) {
        BeanUtils.copyProperties(notification, this);
        this.id = notification.getId();
        this.board = board;
        this.summary = summary;
        this.fromUser = new UserVO(notification.getFromUser());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public UserVO getFromUser() {
        return fromUser;
    }

    public void setFromUser(UserVO fromUser) {
        this.fromUser = fromUser;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
