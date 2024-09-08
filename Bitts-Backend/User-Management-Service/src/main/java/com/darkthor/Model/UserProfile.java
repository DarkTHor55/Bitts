package com.darkthor.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long profileId;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] photo;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] resume;

    // List of Education objects as an embedded collection
    @ElementCollection
    @CollectionTable(name = "education", joinColumns = @JoinColumn(name = "profile_id"))
    @AttributeOverrides({
            @AttributeOverride(name = "degree", column = @Column(name = "degree")),
            @AttributeOverride(name = "institution", column = @Column(name = "institution")),
            @AttributeOverride(name = "startDate", column = @Column(name = "start_date")),
            @AttributeOverride(name = "endDate", column = @Column(name = "end_date"))
    })
    private List<Education> education = new ArrayList<>();

    // Set of skills (strings)
    @ElementCollection
    @CollectionTable(name = "skills", joinColumns = @JoinColumn(name = "profile_id"))
    @Column(name = "skill")
    private Set<String> skills;

    private String codingProfile;
    private String projectProfile;
    private String linkedinProfile;

    @OneToOne(mappedBy = "userProfile")
    private User user;
}
