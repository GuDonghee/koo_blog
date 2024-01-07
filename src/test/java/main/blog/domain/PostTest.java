package main.blog.domain;

import main.blog.domain.Post;
import main.blog.exception.InvalidPostException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PostTest {

    private User dummyUser = null;

    @DisplayName("Post를 생성한다.")
    @Test
    void create() {
        // given
        String title = "포스트 제목";
        String description = "포스트 내용";

        // when
        Post actual = new Post(title, description, dummyUser);

        // then
        assertThat(actual.getTitle()).isEqualTo("포스트 제목");
        assertThat(actual.getDescription()).isEqualTo("포스트 내용");
    }

    @DisplayName("Post를 생성 할 때, 제목이 null이면 예외가 발생한다.")
    @Test
    void create_nullTitle() {
        // given
        String invalidTitle = null;
        String description = "포스트 내용";

        // when & then
        assertThatThrownBy(() -> new Post(invalidTitle, description, dummyUser))
                .isInstanceOf(InvalidPostException.class)
                .hasMessage("포스트 제목은 1 ~ 10 글자 사이의 한글 또는 영어만 입력해주세요.");
    }


    @DisplayName("Post를 생성 할 때, 제목이 1 ~ 10 글자사이의 한글 또는 영어가 아니면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "##$제목", "제133목"})
    void create_invalidTitle(String invalidTitle) {
        // given
        String description = "포스트 내용";

        // when & then
        assertThatThrownBy(() -> new Post(invalidTitle, description, dummyUser))
                .isInstanceOf(InvalidPostException.class)
                .hasMessage("포스트 제목은 1 ~ 10 글자 사이의 한글 또는 영어만 입력해주세요.");
    }
}
