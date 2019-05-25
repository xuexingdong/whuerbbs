package cn.whuerbbs.backend.service;

import cn.whuerbbs.backend.model.PostCollection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostCollectionService {
    void collect(String userId, long postId);

    void cancelCollect(String userId, long postId);

    Page<PostCollection> getPageable(String userId, Pageable pageable);
}
