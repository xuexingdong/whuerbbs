package cn.whuerbbs.backend.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

public class CommentDTO {
    @NotEmpty
    private long postId;
    @NotEmpty
    @Length(max = 300)
    private String content;

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
