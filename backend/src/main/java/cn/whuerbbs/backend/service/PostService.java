package cn.whuerbbs.backend.service;

import cn.whuerbbs.backend.dto.PostDTO;
import cn.whuerbbs.backend.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PostService {
    void create(String userId, PostDTO postDTO);

    Page<Post> getPageable(Pageable pageable);

    Optional<Post> getById(long postId);
}
