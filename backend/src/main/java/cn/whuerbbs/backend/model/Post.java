package cn.whuerbbs.backend.model;

import cn.whuerbbs.backend.enumeration.Board;
import cn.whuerbbs.backend.model.base.BaseModel;
import cn.whuerbbs.backend.model.base.Identifiable;

import java.time.LocalDateTime;

public class Post extends BaseModel implements Identifiable<Long> {
    private Long id;
    private String title;
    private String content;
    private Board board;
    private long likeCount;
    private long commentCount;

    private String userId;
    private LocalDateTime lastActiveAt;

    private User user;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getLastActiveAt() {
        return lastActiveAt;
    }

    public void setLastActiveAt(LocalDateTime lastActiveAt) {
        this.lastActiveAt = lastActiveAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
