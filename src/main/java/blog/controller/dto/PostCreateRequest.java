package blog.controller.dto;

import blog.domain.Post;
import jakarta.validation.constraints.NotNull;

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

    public Post toEntity() {
        return new Post(title, description);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
