package com.groupfour.MedicalCare.Model.DTO;

import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OdsustvoDTO {
    private LocalDate[] datumVreme;
}
