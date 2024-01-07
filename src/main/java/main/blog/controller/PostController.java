package main.blog.controller;

import jakarta.validation.Valid;
import main.auth.controller.AuthenticationPrincipal;
import main.blog.controller.dto.PostCreateRequest;
import main.blog.service.PostService;
import main.blog.service.dto.PostResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

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
        Long postId = this.postService.create(request, userId);
        return ResponseEntity.created(URI.create("/post/" + postId)).build();
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> findPosts() {
        List<PostResponse> responses = this.postService.findPosts();
        return ResponseEntity.ok(responses);
    }
}
