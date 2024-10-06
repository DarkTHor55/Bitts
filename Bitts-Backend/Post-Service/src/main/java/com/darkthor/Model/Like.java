package com.darkthor.Model;


import jakarta.persistence.*;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long likeId;

    private Long userId; // The ID of the user who liked the post

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "postId")
    private Post post; // The post that was liked
}
