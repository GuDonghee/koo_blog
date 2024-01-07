package blog.controller.dto;

import blog.domain.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserCreateRequest {

    @NotBlank(message = "사용자 닉네임은 1 ~ 10 글자 사이의 한글 또는 영어만 입력해주세요.")
    @Size(min = 1, max = 10, message = "사용자 닉네임은 1 ~ 10 글자 사이의 한글 또는 영어만 입력해주세요.")
    private String name;

    @NotNull
    private String email;

    @NotNull
    private String password;

    private UserCreateRequest() {
    }

    public UserCreateRequest(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User toEntity() {
        return new User(name, email, password);
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
