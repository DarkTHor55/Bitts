package com.darkthor.Response;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileResponse {
    private String email;
    private String staues;
}
