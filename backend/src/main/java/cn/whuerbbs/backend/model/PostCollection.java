package cn.whuerbbs.backend.model;

import cn.whuerbbs.backend.model.base.Identifiable;

import java.time.LocalDateTime;

public class PostCollection implements Identifiable<Long> {
    private Long id;
    private long postId;
    private String userId;
    private LocalDateTime createdAt;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
