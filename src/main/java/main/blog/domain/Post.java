package main.blog.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import main.blog.exception.InvalidPostException;

import java.util.regex.Pattern;

@Entity
@Table(name = "posts")
public class Post {

    private static final Pattern TITLE_PATTERN = Pattern.compile("^[a-zA-Zㄱ-ㅎ가-힣]{1,1}[a-zA-Zㄱ-ㅎ가-힣 ]{0,9}$");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(nullable = false)
    private String title;

    @NotEmpty
    @Column(nullable = false)
    private String description;

    protected Post() {
    }

    public Post(String title, String description) {
        validate(title, description);
        this.title = title;
        this.description = description;
    }

    private void validate(String title, String description) {
        if (title == null || !TITLE_PATTERN.matcher(title).matches()) {
            throw new InvalidPostException("포스트 제목은 1 ~ 10 글자 사이의 한글 또는 영어만 입력해주세요.");
        }
        if (description == null || description.isEmpty()) {
            throw new InvalidPostException("포스트 내용은 최소 한글자 이상 입력해주세요.");
        }
    }

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
