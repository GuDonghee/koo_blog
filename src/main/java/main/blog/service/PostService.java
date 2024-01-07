package main.blog.service;

import jakarta.transaction.Transactional;
import main.blog.controller.dto.PostCreateRequest;
import main.blog.domain.Post;
import main.blog.domain.User;
import main.blog.repository.PostRepository;
import main.blog.repository.UserRepository;
import main.blog.service.dto.PostDetailResponse;
import main.blog.service.dto.PostResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Long create(PostCreateRequest request, Long userId) {
        User user = this.userRepository.getById(userId);
        Post post = request.toEntity(user);
        postRepository.save(post);
        return post.getId();
    }

    public List<PostResponse> findPosts() {
        List<Post> posts = this.postRepository.findAll();
        return posts.stream()
                .map(post -> new PostResponse(post.getId(), post.getTitle(), post.getDescription()))
                .toList();
    }

    public PostDetailResponse findPost(Long postId) {
        Post post = this.postRepository.getById(postId);
        return new PostDetailResponse(post);
    }
}
