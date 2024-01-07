package main.blog.service;

import jakarta.transaction.Transactional;
import main.DatabaseCleaner;
import main.blog.exception.NotFoundUserException;
import main.blog.controller.dto.PostCreateRequest;
import main.blog.domain.User;
import main.blog.exception.NotFoundPostException;
import main.blog.repository.UserRepository;
import main.blog.service.dto.PostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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

    @DisplayName("전체 포스트 목록을 조회한다.")
    @Test
    void findPosts() {
        // given
        User user = new User("데이빗", "koo@koo.com", "test1234%#");
        User savedUser = this.userRepository.save(user);

        PostCreateRequest firstRequest = new PostCreateRequest("첫번째임다", "포스트 내용");
        postService.create(firstRequest, savedUser.getId());

        PostCreateRequest secondRequest = new PostCreateRequest("두번째임다", "포스트 내용");
        postService.create(secondRequest, savedUser.getId());

        PostCreateRequest thirdRequest = new PostCreateRequest("세번째임다", "포스트 내용");
        postService.create(thirdRequest, savedUser.getId());

        // when
        List<PostResponse> posts = this.postService.findPosts();

        // then
        assertThat(posts).hasSize(3);
    }

    @DisplayName("포스트를 상세 조회한다.")
    @Test
    void findPost() {
        // given
        User user = new User("데이빗", "koo@koo.com", "test1234%#");
        User savedUser = this.userRepository.save(user);

        PostCreateRequest firstRequest = new PostCreateRequest("첫번째임다", "포스트 내용");
        postService.create(firstRequest, savedUser.getId());

        PostCreateRequest secondRequest = new PostCreateRequest("두번째임다", "포스트 내용");
        postService.create(secondRequest, savedUser.getId());

        PostCreateRequest thirdRequest = new PostCreateRequest("세번째임다", "포스트 내용");
        postService.create(thirdRequest, savedUser.getId());

        // when
        PostResponse post = this.postService.findPost(1L);

        // then
        assertThat(post.getId()).isEqualTo(1);
    }

    @DisplayName("포스트를 상세 조회 할 때, 일치하는 포스트가 없으면 예외가 발생한다.")
    @Test
    void findPost_not_found() {
        // given
        Long invalidPostId = 1L;

        // when & then
        assertThatThrownBy(() -> this.postService.findPost(invalidPostId))
                .isInstanceOf(NotFoundPostException.class)
                .hasMessage(String.format("ID: %d 와 일치하는 포스트가 없습니다.", invalidPostId));
    }
}
