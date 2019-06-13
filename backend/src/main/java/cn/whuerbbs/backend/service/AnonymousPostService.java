package cn.whuerbbs.backend.service;

import cn.whuerbbs.backend.dto.AnonymousPostDTO;
import cn.whuerbbs.backend.model.AnonymousPost;

import java.util.Optional;

public interface AnonymousPostService {
    void publish(String userId, AnonymousPostDTO anonymousPostDTO);

    Optional<AnonymousPost> getByPostId(long postId);
}
