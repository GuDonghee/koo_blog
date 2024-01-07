package main.blog.controller.dto;

import main.blog.domain.Post;
import jakarta.validation.constraints.NotNull;
import main.blog.domain.User;

public class PostCreateRequest {

    @NotNull
    private String title;

    @NotNull
    private String description;

    private PostCreateRequest() {
    }

    public PostCreateRequest(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Post toEntity(User user) {
        return new Post(title, description, user);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
