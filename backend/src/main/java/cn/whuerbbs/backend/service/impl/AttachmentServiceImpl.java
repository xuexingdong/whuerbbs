package cn.whuerbbs.backend.service.impl;

import cn.whuerbbs.backend.exception.BusinessException;
import cn.whuerbbs.backend.mapper.AttachmentMapper;
import cn.whuerbbs.backend.mapper.PostAttachmentMapper;
import cn.whuerbbs.backend.model.Attachment;
import cn.whuerbbs.backend.model.PostAttachment;
import cn.whuerbbs.backend.service.AttachmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class AttachmentServiceImpl implements AttachmentService {
    private static final Logger logger = LoggerFactory.getLogger(AttachmentServiceImpl.class);

    @Value("${filepath}")
    private String filePath;

    @Autowired
    private AttachmentMapper attachmentMapper;

    @Autowired
    private PostAttachmentMapper postAttachmentMapper;

    @Override
    public Attachment upload(String userId, MultipartFile file) {
        var originalFilename = file.getOriginalFilename();
        if (Objects.isNull(originalFilename)) {
            throw new BusinessException("文件名不能为空");
        }
        var uuid = UUID.randomUUID().toString().replaceAll("-", "");
        var suffix = originalFilename.substring(originalFilename.lastIndexOf('.'));
        var storeFilename = Base64.getUrlEncoder().encodeToString(uuid.getBytes());
        var dest = new File(filePath + "/" + storeFilename + suffix);
        try {
            file.transferTo(dest.getAbsoluteFile());
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new BusinessException("上传文件失败");
        }
        var attachment = new Attachment();
        attachment.setId(uuid);
        attachment.setName(originalFilename);
        attachment.setPath(dest.toString());
        attachment.setCreatedAt(LocalDateTime.now());
        attachmentMapper.insert(attachment);
        return attachment;
    }

    @Override
    public List<Attachment> getByPostId(long postId) {
        var postAttachments = postAttachmentMapper.selectByPostId(postId);
        if (postAttachments.isEmpty()) {
            return List.of();
        }
        return attachmentMapper.selectByIds(postAttachments.stream().map(PostAttachment::getAttachmentId).collect(Collectors.toList()));
    }

    @Override
    public Optional<Attachment> getFirstByPostId(long postId) {
        var postAttachment = new PostAttachment();
        postAttachment.setPostId(postId);
        var postAttachments = postAttachmentMapper.selectByPostId(postId);
        if (postAttachments.isEmpty()) {
            return Optional.empty();
        }
        return attachmentMapper.selectById(postAttachments.get(0).getAttachmentId());
    }

    @Override
    public Optional<Attachment> getById(String id) {
        return attachmentMapper.selectById(id);
    }
}
