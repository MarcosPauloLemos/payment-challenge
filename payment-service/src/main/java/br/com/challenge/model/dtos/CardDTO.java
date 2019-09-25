package br.com.challenge.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardDTO {
    private String holderName;
    private Long number;
    private LocalDate expirationDate;
    private Integer cvv;
}
