package cn.whuerbbs.backend.mapper;

import cn.whuerbbs.backend.model.AnonymousPost;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface AnonymousPostMapper {
    int insert(AnonymousPost anonymousPost);

    Optional<AnonymousPost> selectByPostId(long postId);
}
