package blog.domain;

import blog.exception.InvalidPostException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

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
        validateTitle(title);
        this.title = title;
        this.description = description;
    }

    private void validateTitle(String title) {
        if (title == null || !TITLE_PATTERN.matcher(title).matches()) {
            throw new InvalidPostException("포스트 제목은 1 ~ 10 글자 사이의 한글 또는 영어만 입력해주세요.");
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
