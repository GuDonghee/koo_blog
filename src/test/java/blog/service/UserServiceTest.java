package blog.service;

import blog.DatabaseCleaner;
import blog.controller.dto.UserCreateRequest;
import blog.exception.InvalidUserException;
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

    @DisplayName("회원가입을 한다.")
    @Test
    void signUp() {
        // given
        UserCreateRequest request = new UserCreateRequest("데이빗", "koo@koo.com", "test1234!@");

        // when
        Long userId = userService.signUp(request);

        // then
        assertThat(userId).isEqualTo(1L);
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
}
