package blog.domain;

import blog.exception.InvalidUserException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserTest {

    @DisplayName("User를 생성한다.")
    @Test
    void createUser() {
        // given
        String name = "데이빗";
        String email = "koo@koo.com";
        String password = "test123!@";

        // when
        User user = new User(name, email, password);

        // then
        assertThat(user).isInstanceOf(User.class);
    }

    @DisplayName("User를 생성 할 때, name이 1~10자 사이의 한글또는 영어가 아니면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"!", "쿠쿠쿠쿠쿠쿠쿠쿠쿠쿠쿠", "구동!", "데 이 빗", " 제임스", "알렉스 "})
    void createUser_invalidName(String invalidName) {
        // given
        String email = "koo@koo.com";
        String password = "test123!@";

        // when  & then
        assertThatThrownBy(() -> new User(invalidName, email, password))
                .isInstanceOf(InvalidUserException.class)
                .hasMessage("사용자 닉네임은 1 ~ 10 글자 사이의 한글 또는 영어만 입력해주세요.");
    }
}
