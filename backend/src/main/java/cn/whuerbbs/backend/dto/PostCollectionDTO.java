package cn.whuerbbs.backend.dto;

import javax.validation.constraints.NotNull;

public class PostCollectionDTO {
    @NotNull
    private Long postId;

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
