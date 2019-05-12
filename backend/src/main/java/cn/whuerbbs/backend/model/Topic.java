package cn.whuerbbs.backend.model;

import cn.whuerbbs.backend.enumeration.Board;
import cn.whuerbbs.backend.model.base.BaseModel;
import cn.whuerbbs.backend.model.base.Identifiable;

public class Topic extends BaseModel implements Identifiable<Long> {
    private Long id;
    private Board board;
    private String title;
    private String description;
    private String attachmentId;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

}
