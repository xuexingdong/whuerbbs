package cn.whuerbbs.backend.service;

import cn.whuerbbs.backend.dto.CommentDTO;
import cn.whuerbbs.backend.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    Page<Comment> getPageable(long postId, Pageable pageable);

    Comment commentPost(String userId, CommentDTO commentDTO);
}
