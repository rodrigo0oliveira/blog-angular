package br.com.backend.blog_comments.application;

import br.com.backend.blog_comments.infra.repository.CommentRepository;
import br.com.backend.blog_comments.model.Comment;
import br.com.backend.blog_comments.model.CommentDto;
import br.com.backend.blog_comments.model.CommentRequired;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public String createComment(CommentRequired commentRequired) {
        Comment savedComment = Comment.builder()
                .createdAt(LocalDateTime.now())
                .userName(commentRequired.userName())
                .content(commentRequired.content())
                .idArticle(commentRequired.idArticle())
                .build();

        commentRepository.save(savedComment);

        return "Comment created: " + savedComment.getId();
    }

    public Set<CommentDto> findCommentByArticleId(Long articleId) {
        return commentRepository.findCommentsByIdArticle(articleId);
    }
}
