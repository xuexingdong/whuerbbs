package cn.whuerbbs.backend.service;

import cn.whuerbbs.backend.model.Attachment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface AttachmentService{
    Attachment upload(String userId, MultipartFile file);

    List<Attachment> getByPostId(long postId);

    Optional<Attachment> getFirstByPostId(long postId);

    Optional<Attachment> getById(String id);
}
