package cn.whuerbbs.backend.model;

import cn.whuerbbs.backend.model.base.Identifiable;

public class PostAttachment implements Identifiable<Long> {
    private Long id;
    private long postId;
    private String attachmentId;

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

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }
}
