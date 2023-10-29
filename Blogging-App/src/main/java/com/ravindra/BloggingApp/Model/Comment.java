package com.ravindra.BloggingApp.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;

    @Column(nullable = false)
    private String commentBody;

    private LocalDateTime commentCreationDateTime;

    @ManyToOne
    @JoinColumn(name = "fk_commenter_id")
    private User commenter;

    @ManyToOne
    @JoinColumn(name = "fk_post_id")
    private Post blogPost;
}
