package cn.whuerbbs.backend.vo;

import cn.whuerbbs.backend.enumeration.Campus;
import cn.whuerbbs.backend.enumeration.TradeCategory;
import cn.whuerbbs.backend.model.Post;
import cn.whuerbbs.backend.model.Topic;

import java.util.List;

public class SecondhandPostListVO extends PostListVO {
    private TradeCategory tradeCategory;
    private Campus campus;

    public SecondhandPostListVO(Post post, String image, List<Topic> topics, TradeCategory tradeCategory, Campus campus) {
        super(post, image, topics);
        this.tradeCategory = tradeCategory;
        this.campus = campus;
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
}
