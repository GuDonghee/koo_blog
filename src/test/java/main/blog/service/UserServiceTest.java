package main.blog.service;

import main.DatabaseCleaner;
import main.blog.controller.dto.UserCreateRequest;
import main.blog.exception.DuplicateUserException;
import main.blog.exception.InvalidUserException;
import main.blog.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void setUp() {
        this.databaseCleaner.execute();
    }

    @DisplayName("회원가입을 할 때, 닉네임이 1~10자 사이의 한글또는 영어가 아니면 예외가 발생한다.")
    @Test
    void signUp_invalidName() {
        // given
        String invalidName = "열글자를초과하는닉네임은안됩니다";
        UserCreateRequest request = new UserCreateRequest(invalidName, "koo@koo.com", "test1234!@");

        // when  & then
        assertThatThrownBy(() -> this.userService.signUp(request))
                .isInstanceOf(InvalidUserException.class)
                .hasMessage("사용자 닉네임은 1 ~ 10 글자 사이의 한글 또는 영어만 입력해주세요.");
    }

    @DisplayName("회원가입을 할 때, 이메일이 이메일형식이 아니면 예외가 발생한다.")
    @Test
    void signUp_invalidEmail() {
        // given
        String invalidName = null;
        UserCreateRequest request = new UserCreateRequest("데이빗", invalidName, "test1234!@");

        // when  & then
        assertThatThrownBy(() -> this.userService.signUp(request))
                .isInstanceOf(InvalidUserException.class)
                .hasMessage("사용자 이메일은 이메일 형식으로 입력해주세요.");
    }

    @DisplayName("회원가입을 할 때, 이미 저장된 이메일과 중복되면 예외가 발생한다.")
    @Test
    void signUp_duplicateEmail() {
        // given
        UserCreateRequest request = new UserCreateRequest("데이빗", "koo@koo.com", "test1234!@");
        userService.signUp(request);

        // when  & then
        assertThatThrownBy(() -> this.userService.signUp(request))
                .isInstanceOf(DuplicateUserException.class)
                .hasMessage("이미 저장된 이메일입니다.");
    }

    @DisplayName("회원가입을 할 때, 비밀번호가 6~15자 사이의 영어,숫자,특수기호 조합이 아니면 예외가 발생한다.")
    @Test
    void signUp_invalidPassword() {
        // given
        String invalidPassword = null;
        UserCreateRequest request = new UserCreateRequest("데이빗", "koo@koo.com", invalidPassword);

        // when  & then
        assertThatThrownBy(() -> this.userService.signUp(request))
                .isInstanceOf(InvalidUserException.class)
                .hasMessage("사용자 비밀번호는 6 ~ 15자 사이의 영어,숫자,특수기호 조합으로 입력해주세요.");
    }
}
