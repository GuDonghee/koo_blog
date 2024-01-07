package blog.service;

import blog.DatabaseCleaner;
import blog.controller.dto.PostCreateRequest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class PostServiceTest {

    @Autowired
    private PostService postService;

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
        PostCreateRequest request = new PostCreateRequest("포스트 제목", "포스트 내용");

        // when
        Long postId = postService.create(request);

        // then
        assertThat(postId).isEqualTo(1L);
    }
}
