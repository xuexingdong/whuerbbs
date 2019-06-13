package cn.whuerbbs.backend.mapper;

import cn.whuerbbs.backend.model.Attachment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface AttachmentMapper {
    int insert(Attachment attachment);

    List<Attachment> selectByIds(List<String> ids);

    Optional<Attachment> selectById(String attachmentId);
}
