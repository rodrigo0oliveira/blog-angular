package br.com.backend.blog_comments.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank
    private String content;

    @NotBlank
    private String userName;

    @NotBlank
    private String idArticle;

    @NotBlank
    private LocalDateTime createdAt;

}
