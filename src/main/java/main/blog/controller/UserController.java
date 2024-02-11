package main.blog.controller;

import jakarta.validation.Valid;
import main.blog.controller.dto.UserCreateRequest;
import main.blog.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;

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
        InetAddress local = null;
        try {
            local = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        String ip = local.getHostAddress();
        return ResponseEntity.created(URI.create("/users/" + userId + ":" + ip)).build();
    }
}
