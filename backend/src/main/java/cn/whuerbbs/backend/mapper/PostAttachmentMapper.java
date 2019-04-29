package cn.whuerbbs.backend.mapper;

import cn.whuerbbs.backend.model.PostAttachment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PostAttachmentMapper {
    int insertBatch(List<PostAttachment> postAttachments);

    List<PostAttachment> selectByPostId(long postId);

    Optional<PostAttachment> selectFirstByPostId(long id);
}
