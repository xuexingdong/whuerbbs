package cn.whuerbbs.backend.vo;

import cn.whuerbbs.backend.enumeration.Campus;
import cn.whuerbbs.backend.enumeration.TradeCategory;
import cn.whuerbbs.backend.model.Post;
import cn.whuerbbs.backend.model.Topic;

import java.util.List;

public class SecondhandPostVO extends PostVO {
    private TradeCategory tradeCategory;
    private Campus campus;

    public SecondhandPostVO(Post post, List<String> images, List<Topic> topics, boolean collected, TradeCategory tradeCategory, Campus campus) {
        super(post, images, topics, collected);
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
