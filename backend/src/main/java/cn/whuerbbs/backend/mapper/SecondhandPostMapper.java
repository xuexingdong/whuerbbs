package cn.whuerbbs.backend.mapper;

import cn.whuerbbs.backend.model.SecondhandPost;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface SecondhandPostMapper {
    int insert(SecondhandPost secondHand);

    Optional<SecondhandPost> selectByPostId(long postId);
}
