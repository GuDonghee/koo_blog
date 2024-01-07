package main.blog.repository;

import main.blog.domain.Post;
import main.blog.exception.NotFoundPostException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    default Post getById(Long id) {
        return this.findById(id)
                .orElseThrow(() -> new NotFoundPostException(String.format("ID: %d 와 일치하는 포스트가 없습니다.", id)));
    }
}
