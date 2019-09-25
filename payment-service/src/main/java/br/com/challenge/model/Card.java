package br.com.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    private Long clientId;
    private String holderName;
    private Long number;
    private LocalDate expirationDt;
    private Integer cvv;
    private BigDecimal amount;
    private Long paymentNumber;

    public String getExpirationDate(){
        return expirationDt.toString();
    }
}
