package cn.whuerbbs.backend.vo;

import cn.whuerbbs.backend.model.Comment;

import java.util.List;

public class CommentListVO extends CommentVO {
    private List<CommentVO> subComments;
    private long subCommentCount;

    public CommentListVO(Comment comment, List<CommentVO> subComments, long subCommentCount) {
        super(comment);
        this.subComments = subComments;
        this.subCommentCount = subCommentCount;
    }

    public List<CommentVO> getSubComments() {
        return subComments;
    }

    public void setSubComments(List<CommentVO> subComments) {
        this.subComments = subComments;
    }

    public long getSubCommentCount() {
        return subCommentCount;
    }

    public void setSubCommentCount(long subCommentCount) {
        this.subCommentCount = subCommentCount;
    }

}
