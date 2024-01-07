package blog.service;

import blog.controller.dto.UserCreateRequest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

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
}