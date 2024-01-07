package main.auth.service;

import jakarta.transaction.Transactional;
import main.auth.controller.dto.LoginRequest;
import main.auth.repository.UserAuthRepository;
import main.blog.domain.User;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AuthService {

    private final UserAuthRepository userRepository;
    private final TokenCreator tokenCreator;

    public AuthService(UserAuthRepository userRepository, TokenCreator tokenCreator) {
        this.userRepository = userRepository;
        this.tokenCreator = tokenCreator;
    }

    public String login(LoginRequest request) {
        User user = this.userRepository.getByEmailAndPassword(request.getEmail(), request.getPassword());
        return tokenCreator.createAccessToken(user.getId());
    }
}
