package cn.whuerbbs.backend.service;

import cn.whuerbbs.backend.dto.SecondhandPostDTO;
import cn.whuerbbs.backend.model.SecondhandPost;

import java.util.Optional;

public interface SecondhandPostService {
    void publish(String userId, SecondhandPostDTO secondHandPostDTO);

    Optional<SecondhandPost> getByPostId(long postId);
}
