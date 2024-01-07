package blog.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
}
