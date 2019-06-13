package cn.whuerbbs.backend.model;

import cn.whuerbbs.backend.model.base.Identifiable;

public class AnonymousPost implements Identifiable<Long> {
    private Long id;
    private long postId;
    private String anonymousName;
    private Post post;

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

    public String getAnonymousName() {
        return anonymousName;
    }

    public void setAnonymousName(String anonymousName) {
        this.anonymousName = anonymousName;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
