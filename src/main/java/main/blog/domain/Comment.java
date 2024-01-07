package main.blog.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import main.blog.exception.InvalidCommentException;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    protected Comment() {
    }

    public Comment(String description, User user, Post post) {
        validate(description);
        this.description = description;
        this.user = user;
        this.post = post;
    }

    private void validate(String description) {
        if (description == null || description.isEmpty()) {
            throw new InvalidCommentException("코멘트는 최소 1글자 이상 입력해주세요.");
        }
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public User getUser() {
        return user;
    }

    public Post getPost() {
        return post;
    }
}
