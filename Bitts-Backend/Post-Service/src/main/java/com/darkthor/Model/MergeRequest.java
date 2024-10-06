package com.darkthor.Model;


import jakarta.persistence.*;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "merge_requests")
public class MergeRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long requestId;

    private Long senderId; // The ID of the user sending the merge request
    private String senderProfileLink; // The profile link of the sender

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "postId")
    private Post post; // The post to which the merge request is sent
}
