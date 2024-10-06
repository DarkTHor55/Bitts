package com.darkthor.Model;


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

    private Long userId; // The ID of the user who commented

    @Lob
    private String content; // The comment content

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "postId")
    private Post post; // The post that was commented on
}

