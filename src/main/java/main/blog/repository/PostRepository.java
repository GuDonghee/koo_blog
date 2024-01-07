package main.blog.repository;

import main.blog.domain.Post;
import main.blog.exception.NotFoundPostException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT DISTINCT p "
            + "FROM Post p "
            + "LEFT JOIN FETCH p.comments c "
            + "WHERE p.id = :id ")
    Optional<Post> findByIdWithComment(Long id);

    default Post getById(Long id) {
        return this.findByIdWithComment(id)
                .orElseThrow(() -> new NotFoundPostException(String.format("ID: %d 와 일치하는 포스트가 없습니다.", id)));
    }
}
