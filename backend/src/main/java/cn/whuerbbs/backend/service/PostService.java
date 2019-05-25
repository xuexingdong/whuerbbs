package cn.whuerbbs.backend.service;

import cn.whuerbbs.backend.dto.PostDTO;
import cn.whuerbbs.backend.enumeration.Board;
import cn.whuerbbs.backend.model.Post;
import cn.whuerbbs.backend.vo.PostListVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostService {
    void create(String userId, PostDTO postDTO);

    Page<Post> getPageableByBoard(Pageable pageable, Board board);

    Optional<Post> getById(long postId);

    Page<Post> getPostsPageableByTopicId(Pageable pageable, int topicId);

    Page<Post> getHotPostsPageableByTopicId(Pageable pageable, int topicId);

    PostListVO getPostListVO(Post post);

    List<PostListVO> getPostListVO(List<Long> postIds);

    Post addAnonymousInfo(Post post);
}
