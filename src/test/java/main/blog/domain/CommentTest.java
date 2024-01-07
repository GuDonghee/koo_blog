package main.blog.domain;

import main.blog.exception.InvalidCommentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CommentTest {

    private User dummyUser = null;
    private Post dummyPost = null;

    @DisplayName("Comment를 생성한다.")
    @Test
    void create() {
        // given
        String description = "이 글의 출처는 어딘가요?";

        // when
        Comment actual = new Comment(description, dummyUser, dummyPost);

        // then
        assertThat(actual.getDescription()).isEqualTo("이 글의 출처는 어딘가요?");
    }

    @DisplayName("Comment를 생성 할 때, 코멘트 내용이 최소 한글자 이상이 아니면 예외가 발생한다.")
    @Test
    void create_invalid_length() {
        // given
        String description = "";

        // when & then
        assertThatThrownBy(() -> new Comment(description, dummyUser, dummyPost))
                .isInstanceOf(InvalidCommentException.class)
                .hasMessage("코멘트는 최소 1글자 이상 입력해주세요.");
    }
}
