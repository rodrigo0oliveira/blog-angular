package br.com.backend.blog_comments.infra.repository;

import br.com.backend.blog_comments.model.Comment;
import br.com.backend.blog_comments.model.CommentDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select new br.com.backend.blog_comments.model.CommentDto" +
            "(c.content,c.userName,c.createdAt) from Comment c where c.id = :idArticle")
    Set<CommentDto> findCommentsByIdArticle(Long idArticle);
}
