package com.groupfour.MedicalCare.Model.DTO;

import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MedicinskaSestraKalendarDTO {
    private LocalDateTime start;
    private LocalDateTime end;
    private String title;
}
