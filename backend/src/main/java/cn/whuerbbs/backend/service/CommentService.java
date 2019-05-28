package cn.whuerbbs.backend.service;

import cn.whuerbbs.backend.dto.CommentDTO;
import cn.whuerbbs.backend.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Page<Comment> getPageable(long postId, Pageable pageable);

    void commentPost(String userId, CommentDTO commentDTO);

    List<Comment> getHotComments(long postId);

    Optional<Comment> getCommentById(long commentId);

    List<Comment> getCommentsByParentId(long parentCommentId);

    void deleteCommentById(long commentId);

    Page<Comment> getSubCommentsPageable(long commentId, Pageable pageable);

    List<Comment> getAllSubComments(long parentCommentId);
}
