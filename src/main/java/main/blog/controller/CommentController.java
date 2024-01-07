package main.blog.controller;

import jakarta.validation.Valid;
import main.auth.controller.AuthenticationPrincipal;
import main.blog.controller.dto.CommentCreateRequest;
import main.blog.controller.dto.PostCreateRequest;
import main.blog.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<Void> create(
            @AuthenticationPrincipal Long userId,
            @RequestBody @Valid CommentCreateRequest request
    ) {
        Long commentId = this.commentService.create(request, userId);
        return ResponseEntity.created(URI.create("/comments/" + commentId)).build();
    }
}
