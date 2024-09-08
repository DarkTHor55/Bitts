package com.darkthor.Request;

import com.darkthor.Model.Education;
import lombok.*;

import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileRequest {
    private List<Education> education;
    private Set<String> skills;
    private String codingProfile;
    private String projectProfile;
    private String linkedinProfile;
    private byte[] photo;
    private byte[] resume;
}
