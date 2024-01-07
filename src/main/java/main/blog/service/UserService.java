package main.blog.service;

import main.blog.domain.User;
import main.blog.exception.DuplicateUserException;
import main.blog.repository.UserRepository;
import main.blog.controller.dto.UserCreateRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long signUp(UserCreateRequest request) {
        User user = request.toEntity();
        if (this.userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateUserException("이미 저장된 이메일입니다.");
        }
        User savedUser = this.userRepository.save(user);
        return savedUser.getId();
    }
}
