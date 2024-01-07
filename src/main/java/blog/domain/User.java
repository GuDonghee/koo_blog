package blog.domain;

import blog.exception.InvalidUserException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.regex.Pattern;

@Entity
@Table(name = "users")
public class User {

    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Zㄱ-ㅎ가-힣]{1,10}$");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(nullable = false)
    private String name;

    @NotEmpty
    @Column(nullable = false)
    private String email;

    @NotEmpty
    @Column(nullable = false)
    private String password;

    protected User() {
    }

    public User(String name, String email, String password) {
        validate(name);
        this.name = name;
        this.email = email;
        this.password = password;
    }

    private void validate(String name) {
        if (name == null || !NAME_PATTERN.matcher(name).matches()) {
            throw new InvalidUserException("사용자 닉네임은 1 ~ 10 글자 사이의 한글 또는 영어만 입력해주세요.");
        }
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
