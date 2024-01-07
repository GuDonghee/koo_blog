package main.blog.controller.dto;

import jakarta.validation.constraints.NotNull;
import main.blog.domain.Comment;
import main.blog.domain.Post;
import main.blog.domain.User;

public class CommentCreateRequest {

    @NotNull
    private Long postId;

    @NotNull
    private String description;

    private CommentCreateRequest() {
    }

    public CommentCreateRequest(Long postId, String description) {
        this.postId = postId;
        this.description = description;
    }

    public Long getPostId() {
        return postId;
    }

    public String getDescription() {
        return description;
    }

    public Comment toEntity(User user, Post post) {
        return new Comment(description, user, post);
    }
}
