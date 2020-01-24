package com.groupfour.MedicalCare.Model.DTO;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PromenaLozinkeDTO {
    private String staraLozinka;
    private String novaLozinka;
    private String role;
}
