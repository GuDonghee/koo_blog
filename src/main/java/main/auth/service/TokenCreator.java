package main.auth.service;

import org.springframework.stereotype.Component;

@Component
public class TokenCreator {

    private final TokenProvider tokenProvider;

    public TokenCreator(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    public String createAccessToken(Long userId) {
        return tokenProvider.createAccessToken(String.valueOf(userId));
    }
}
