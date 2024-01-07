package blog.service;

import blog.controller.dto.PostCreateRequest;
import blog.domain.Post;
import blog.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public Long create(PostCreateRequest request) {
        Post post = request.toEntity();
        postRepository.save(post);
        return post.getId();
    }
}
