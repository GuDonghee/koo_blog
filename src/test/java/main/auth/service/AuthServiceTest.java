package main.auth.service;


import main.DatabaseCleaner;
import main.auth.controller.dto.LoginRequest;
import main.auth.exception.NotFoundUserException;
import main.blog.controller.dto.UserCreateRequest;
import main.blog.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void setUp() {
        this.databaseCleaner.execute();
    }

    @DisplayName("로그인을 하면 엑세스토큰을 발급한다.")
    @Test
    void login() {
        // given
        UserCreateRequest userCreateRequest = new UserCreateRequest("데이빗", "koo@koo.com", "test1234!@");
        userService.signUp(userCreateRequest);

        LoginRequest request = new LoginRequest("koo@koo.com", "test1234!@");

        // when
        String accessToken = this.authService.login(request);

        // then
        assertThat(accessToken).isNotNull();
    }

    @DisplayName("로그인 할 때, 이메일과 비밀번호가 일치하는 회원이 없으면 예외가 발생한다.")
    @Test
    void logIn_not_found_user() {
        // given
        LoginRequest request = new LoginRequest("koo@koo.com", "test1234!@");

        // when & then
        assertThatThrownBy(() -> this.authService.login(request))
                .isInstanceOf(NotFoundUserException.class)
                .hasMessage("이메일 또는 비밀번호와 일치하는 회원이 없습니다.");
    }
}
