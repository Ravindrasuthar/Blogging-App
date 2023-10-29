package com.ravindra.BloggingApp.Model;

import com.ravindra.BloggingApp.Model.Enum.PostType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;
    private String postContent;

    @NotBlank
    private String postTitle;

    @Pattern(regexp = "^.*\\.blogspot\\.com$")
    private String postAddress;                 // The web address through which people can find blog online.
    private String postLocation;

    @Enumerated(EnumType.STRING)
    private PostType postType;
    private LocalDateTime postCreationDateTime;

    @ManyToOne
    @JoinColumn(name = "fk_owner_user_id")
    private User postOwner;
}
