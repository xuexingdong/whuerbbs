package cn.whuerbbs.backend.model;

import cn.whuerbbs.backend.enumeration.Campus;
import cn.whuerbbs.backend.enumeration.TradeCategory;
import cn.whuerbbs.backend.model.base.Identifiable;

public class SecondhandPost implements Identifiable<Long> {
    private Long id;
    private long postId;
    private TradeCategory tradeCategory;
    private Campus campus;
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

    public TradeCategory getTradeCategory() {
        return tradeCategory;
    }

    public void setTradeCategory(TradeCategory tradeCategory) {
        this.tradeCategory = tradeCategory;
    }

    public Campus getCampus() {
        return campus;
    }

    public void setCampus(Campus campus) {
        this.campus = campus;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
