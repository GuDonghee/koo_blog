package main.auth.repository;

import main.auth.exception.NotFoundUserException;
import main.blog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAuthRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailAndPassword(String email, String password);

    default User getByEmailAndPassword(String email, String password) {
        return this.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new NotFoundUserException("이메일 또는 비밀번호와 일치하는 회원이 없습니다."));
    }
}
