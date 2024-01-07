package blog.service;

import blog.controller.dto.UserCreateRequest;
import blog.domain.User;
import blog.repository.UserRepository;
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
        User savedUser = this.userRepository.save(user);
        return savedUser.getId();
    }
}
