package cn.whuerbbs.backend.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TradeCategory implements BaseEnum {
    ZONGHE(1, "综合"),
    SHUMAJIADIAN(2, "数码家电"),
    CLOTHES(3, "衣物鞋靴"),
    SHUKANZILIAO(4, "书刊资料"),
    SHENGHUOYONGPIN(5, "生活用品"),
    MEIRONGMEIZHUANG(6, "美容美妆");

    private final int value;
    private final String title;

    TradeCategory(int value, String title) {
        this.value = value;
        this.title = title;
    }

    @Override
    public int value() {
        return value;
    }

    @JsonCreator
    public static TradeCategory fromValue(int value) {
        for (TradeCategory tradeCategory : values()) {
            if (tradeCategory.value == value) {
                return tradeCategory;
            }
        }
        return null;
    }

    public String getTitle() {
        return title;
    }
}
