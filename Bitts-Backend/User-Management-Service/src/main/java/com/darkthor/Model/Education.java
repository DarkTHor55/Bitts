package com.darkthor.Model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Education {
    private String degree;
    private String institution;
    private String startDate;
    private String endDate;
}
