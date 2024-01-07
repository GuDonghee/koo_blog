package main.auth.controller;

import main.auth.controller.dto.LoginRequest;
import main.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public ResponseEntity<Map<String, String>> signUp(@RequestBody LoginRequest request) {
        String accessToken = this.authService.login(request);
        Map<String, String> response = new HashMap<>();
        response.put("token", accessToken);
        return ResponseEntity.ok(response);
    }
}
