package br.com.backend.blog_comments.application;

import br.com.backend.blog_comments.model.Comment;
import br.com.backend.blog_comments.model.CommentDto;
import br.com.backend.blog_comments.model.CommentRequired;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("comments")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<String> createComment(@RequestBody @Valid CommentRequired commentRequired) {
        String message = commentService.createComment(commentRequired);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping
    public ResponseEntity<Set<CommentDto>> getAllCommentByArticleId(@RequestParam Long articleId) {
        Set<CommentDto> comments = commentService.findCommentByArticleId(articleId);
        return ResponseEntity.ok(comments);
    }
}
