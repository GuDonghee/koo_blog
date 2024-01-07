package main.blog.controller;

import main.blog.controller.dto.UserCreateRequest;
import main.blog.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> signUp(@RequestBody @Valid UserCreateRequest request) {
        Long userId = this.userService.signUp(request);
        return ResponseEntity.created(URI.create("/users/" + userId)).build();
    }
}
