package cn.whuerbbs.backend.service;

import java.util.List;

public interface PostAttachmentService {
    void addAll(long postId, List<String> images);
}
