package cn.whuerbbs.backend.mapper;

import cn.whuerbbs.backend.model.PostCollection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PostCollectionMapper {
    int insert(PostCollection postCollection);

    List<PostCollection> selectPageable(String userId);

    int delete(@Param("postId") long postId, @Param("userId") String userId);

    Optional<PostCollection> selectByUserIdAndPostId(@Param("userId") String userId, @Param("postId") long postId);
}
