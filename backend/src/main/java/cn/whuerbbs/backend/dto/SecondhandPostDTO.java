package cn.whuerbbs.backend.dto;

import cn.whuerbbs.backend.enumeration.Board;
import cn.whuerbbs.backend.enumeration.Campus;
import cn.whuerbbs.backend.enumeration.TradeCategory;

import javax.validation.constraints.NotNull;

public class SecondhandPostDTO extends CommonPostDTO {
    private final Board board = Board.SECONDHAND;
    @NotNull
    private TradeCategory tradeCategory;
    @NotNull
    private Campus campus;

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

    public Board getBoard() {
        return board;
    }
}
