package main.blog.service;

import main.blog.repository.PostRepository;
import main.blog.controller.dto.PostCreateRequest;
import main.blog.domain.Post;
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
