package main.blog.controller;

import jakarta.validation.Valid;
import main.auth.controller.AuthenticationPrincipal;
import main.blog.controller.dto.PostCreateRequest;
import main.blog.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<Void> create(
            @AuthenticationPrincipal Long userId,
            @RequestBody @Valid PostCreateRequest request
    ) {
        Long postId = this.postService.create(request);
        return ResponseEntity.created(URI.create("/post/" + postId)).build();
    }
}
