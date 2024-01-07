package main.blog.service;

import jakarta.transaction.Transactional;
import main.blog.controller.dto.PostCreateRequest;
import main.blog.domain.Post;
import main.blog.domain.User;
import main.blog.repository.PostRepository;
import main.blog.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Long create(PostCreateRequest request, Long userId) {
        User user = this.userRepository.getById(userId);
        Post post = request.toEntity(user);
        postRepository.save(post);
        return post.getId();
    }
}
