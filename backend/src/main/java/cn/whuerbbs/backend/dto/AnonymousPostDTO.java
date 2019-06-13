package cn.whuerbbs.backend.dto;

import cn.whuerbbs.backend.enumeration.Board;

import javax.validation.constraints.NotNull;

public class AnonymousPostDTO extends CommonPostDTO {
    private final Board board = Board.ANONYMOUS_POST;
    @NotNull
    private String anonymousName;

    public String getAnonymousName() {
        return anonymousName;
    }

    public void setAnonymousName(String anonymousName) {
        this.anonymousName = anonymousName;
    }

    public Board getBoard() {
        return board;
    }
}
