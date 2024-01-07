package main.blog.service.dto;

import main.blog.domain.Comment;
import main.blog.domain.Post;

import java.util.List;

public class PostDetailResponse {

    private Long id;
    private String title;
    private String description;
    private List<CommentDto> comments;

    public PostDetailResponse() {
    }

    public PostDetailResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.description = post.getDescription();
        this.comments = post.getComments().stream()
                .map(CommentDto::new)
                .toList();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<CommentDto> getComments() {
        return comments;
    }

    public static class CommentDto {

        private Long id;
        private String description;
        private Long userId;
        private String userName;

        public CommentDto() {
        }

        public CommentDto(Comment comment) {
            this.id = comment.getId();
            this.description = comment.getDescription();
            this.userId = comment.getUser().getId();
            this.userName = comment.getUser().getName();
        }

        public Long getId() {
            return id;
        }

        public String getDescription() {
            return description;
        }

        public Long getUserId() {
            return userId;
        }

        public String getUserName() {
            return userName;
        }
    }
}
