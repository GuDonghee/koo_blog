package main.auth.service;

import jakarta.transaction.Transactional;
import main.auth.controller.dto.LoginRequest;
import main.auth.exception.NotFoundUserException;
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

    public Long extractUserId(String accessToken) {
        Long userId = tokenCreator.extractPayload(accessToken);
        if (!this.userRepository.existsById(userId)) {
            throw new NotFoundUserException("저장되지 않은 회원입니다. 올바른 엑세스토큰인지 확인해주세요.");
        }
        return userId;
    }
}
