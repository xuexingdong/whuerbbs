package cn.whuerbbs.backend.service.impl;

import cn.whuerbbs.backend.exception.BusinessException;
import cn.whuerbbs.backend.mapper.PostCollectionMapper;
import cn.whuerbbs.backend.mapper.PostMapper;
import cn.whuerbbs.backend.model.PostCollection;
import cn.whuerbbs.backend.service.PostCollectionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDateTime;

@Service
@Transactional(rollbackFor = Exception.class)
public class PostCollectionServiceImpl implements PostCollectionService {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private PostCollectionMapper postCollectionMapper;

    @Override
    public void collect(String userId, long postId) {
        postMapper.selectById(postId).orElseThrow(() -> new BusinessException("帖子不存在"));
        var postCollection = new PostCollection();
        postCollection.setUserId(userId);
        postCollection.setPostId(postId);
        postCollection.setCreatedAt(LocalDateTime.now());
        try {
            postCollectionMapper.insert(postCollection);
        } catch (DuplicateKeyException ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    @Override
    public void cancelCollect(String userId, long postId) {
        postCollectionMapper.delete(postId, userId);
    }

    @Override
    public Page<PostCollection> getPageable(String userId, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        var postCollections = postCollectionMapper.selectPageable(userId);
        var pageInfo = new PageInfo<>(postCollections);
        return new PageImpl<>(postCollections, pageable, pageInfo.getTotal());
    }

    @Override
    public boolean hasCollected(String userId, long postId) {
        return postCollectionMapper.selectByUserIdAndPostId(userId, postId).isPresent();
    }
}
