package main.blog.repository;

import main.auth.exception.NotFoundUserException;
import main.blog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    default User getById(Long id) {
        return this.findById(id)
                .orElseThrow(() -> new NotFoundUserException(String.format("ID: %d와 일치하는 회원이 없습니다.", id)));
    }
}
