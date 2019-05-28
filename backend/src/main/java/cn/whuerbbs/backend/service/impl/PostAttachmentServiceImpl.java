package cn.whuerbbs.backend.service.impl;

import cn.whuerbbs.backend.mapper.PostAttachmentMapper;
import cn.whuerbbs.backend.model.PostAttachment;
import cn.whuerbbs.backend.service.PostAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class PostAttachmentServiceImpl implements PostAttachmentService {

    @Autowired
    private PostAttachmentMapper postAttachmentMapper;

    @Override
    public void addAll(long postId, List<String> images) {
        var postAttachments = images.stream().map(attachmentId -> {
            var postAttachment = new PostAttachment();
            postAttachment.setPostId(postId);
            postAttachment.setAttachmentId(attachmentId);
            return postAttachment;
        }).collect(Collectors.toList());
        if (!postAttachments.isEmpty()) {
            postAttachmentMapper.insertBatch(postAttachments);
        }
    }
}
