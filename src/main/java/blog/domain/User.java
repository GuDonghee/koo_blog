package blog.domain;

import blog.exception.InvalidUserException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.regex.Pattern;

@Entity
@Table(name = "users")
public class User {

    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Zㄱ-ㅎ가-힣]{1,10}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?!.*\\s)(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{6,15}$");

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
        validate(name, email, password);
        this.name = name;
        this.email = email;
        this.password = password;
    }

    private void validate(String name, String email, String password) {
        if (name == null || !NAME_PATTERN.matcher(name).matches()) {
            throw new InvalidUserException("사용자 닉네임은 1 ~ 10 글자 사이의 한글 또는 영어만 입력해주세요.");
        }
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new InvalidUserException("사용자 이메일은 이메일 형식으로 입력해주세요.");
        }
        if (password == null || !PASSWORD_PATTERN.matcher(password).matches()) {
            throw new InvalidUserException("사용자 비밀번호는 6 ~ 15자 사이의 영어,숫자,특수기호 조합으로 입력해주세요.");
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
