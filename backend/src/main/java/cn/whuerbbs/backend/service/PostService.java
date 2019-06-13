package cn.whuerbbs.backend.service;

import cn.whuerbbs.backend.dto.PostDTO;
import cn.whuerbbs.backend.enumeration.Board;
import cn.whuerbbs.backend.model.Post;
import cn.whuerbbs.backend.vo.PostListVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    void create(String userId, PostDTO postDTO);

    Page<Post> getPageableByBoard(Board board, Pageable pageable);

    Post getById(long postId);

    Page<Post> getPostsPageableByTopicId(long topicId, Pageable pageable);

    Page<Post> getHotPostsPageableByTopicId(long topicId, Pageable pageable);

    PostListVO getPostListVO(Post post);

    List<PostListVO> getPostListVO(List<Long> postIds);

    Post addAnonymousInfo(Post post);

    void deleteById(long postId);
}
