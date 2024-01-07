package main.auth.service;

import main.auth.controller.dto.LoginRequest;
import main.auth.repository.UserAuthRepository;
import main.blog.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AuthService {

    private final UserAuthRepository userRepository;

    public AuthService(UserAuthRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void logIn(LoginRequest request) {
        User user = this.userRepository.getByEmailAndPassword(request.getEmail(), request.getPassword());

    }
}
