package com.darkthor.Request;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OtpValidationRequest {
    private String email;
    private int otp;
}
