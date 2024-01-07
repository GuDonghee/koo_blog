package main.blog.service;

import jakarta.transaction.Transactional;
import main.blog.controller.dto.CommentCreateRequest;
import main.blog.domain.Comment;
import main.blog.domain.Post;
import main.blog.domain.User;
import main.blog.repository.CommentRepository;
import main.blog.repository.PostRepository;
import main.blog.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Long create(CommentCreateRequest request, Long userId) {
        User user = this.userRepository.getById(userId);
        Post post = this.postRepository.getById(request.getPostId());
        Comment comment = request.toEntity(user, post);
        Comment savedComment = this.commentRepository.save(comment);
        return savedComment.getId();
    }
}
