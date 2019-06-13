package cn.whuerbbs.backend.dto;

import cn.whuerbbs.backend.enumeration.Board;

import javax.validation.constraints.NotNull;

public class PostDTO extends CommonPostDTO {
    @NotNull
    private Board board;

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
