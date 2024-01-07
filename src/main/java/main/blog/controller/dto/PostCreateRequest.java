package main.blog.controller.dto;

import jakarta.validation.constraints.NotNull;
import main.blog.domain.Post;
import main.blog.domain.User;

import java.util.ArrayList;

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
