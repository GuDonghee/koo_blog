package main.blog.service;

import main.DatabaseCleaner;
import main.blog.controller.dto.CommentCreateRequest;
import main.blog.domain.Post;
import main.blog.domain.User;
import main.blog.exception.NotFoundPostException;
import main.blog.exception.NotFoundUserException;
import main.blog.repository.PostRepository;
import main.blog.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void setUp() {
        this.databaseCleaner.execute();
    }

    @DisplayName("코멘트를 등록한다.")
    @Test
    void createComment() {
        // given
        User user = new User("데이빗", "koo@koo.com", "test1234%#");
        User savedUser = this.userRepository.save(user);

        Post post = new Post("포스트명", "내용", user);
        Post savedPost = this.postRepository.save(post);

        CommentCreateRequest request = new CommentCreateRequest(savedPost.getId(), "코멘트 내용입니다.");

        // when
        Long commentId = this.commentService.create(request, savedUser.getId());

        // then
        assertThat(commentId).isEqualTo(1L);
    }

    @DisplayName("코멘트를 등록할 때, 일치하는 회원이 없으면 예외가 발생한다.")
    @Test
    void createComment_not_found_user() {
        // given
        Long invalidUserId = 1L;
        Long invalidPostId = 1L;

        CommentCreateRequest request = new CommentCreateRequest(invalidPostId, "코멘트 내용입니다.");

        // when & then
        assertThatThrownBy(() -> this.commentService.create(request, invalidUserId))
                .isInstanceOf(NotFoundUserException.class)
                .hasMessage(String.format("ID: %d와 일치하는 회원이 없습니다.", invalidUserId));
    }

    @DisplayName("포스트를 등록 할 때, 일치하는 포스트가 없으면 예외가 발생한다.")
    @Test
    void createComment_not_found_post() {
        // given
        User user = new User("데이빗", "koo@koo.com", "test1234%#");
        User savedUser = this.userRepository.save(user);

        Long invalidPostId = 1L;

        CommentCreateRequest request = new CommentCreateRequest(invalidPostId, "코멘트 내용입니다.");

        // when & then
        assertThatThrownBy(() -> this.commentService.create(request, savedUser.getId()))
                .isInstanceOf(NotFoundPostException.class)
                .hasMessage(String.format("ID: %d 와 일치하는 포스트가 없습니다.", invalidPostId));
    }
}
