package main.blog.service;

import jakarta.transaction.Transactional;
import main.DatabaseCleaner;
import main.auth.exception.NotFoundUserException;
import main.blog.controller.dto.PostCreateRequest;
import main.blog.domain.User;
import main.blog.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void setUp() {
        this.databaseCleaner.execute();
    }

    @DisplayName("포스트를 등록한다.")
    @Test
    void createPost() {
        // given
        User user = new User("데이빗", "koo@koo.com", "test1234%#");
        User savedUser = this.userRepository.save(user);
        PostCreateRequest request = new PostCreateRequest("포스트 제목", "포스트 내용");

        // when
        Long postId = postService.create(request, savedUser.getId());

        // then
        assertThat(postId).isEqualTo(1L);
    }

    @DisplayName("포스트를 등록 할 때, 일치하는 회원이 없으면 예외가 발생한다.")
    @Test
    void createPost_not_found_user() {
        // given
        Long invalidUserId = 1L;
        PostCreateRequest request = new PostCreateRequest("포스트 제목", "포스트 내용");

        // when & then
        assertThatThrownBy(() -> this.postService.create(request, invalidUserId))
                .isInstanceOf(NotFoundUserException.class)
                .hasMessage(String.format("ID: %d와 일치하는 회원이 없습니다.", invalidUserId));
    }
}
