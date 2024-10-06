package com.darkthor.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long commentId;
    private Long userId;
    @Lob
    private String content;
    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "postId")
    @JsonIgnore
    private Post post;
}

