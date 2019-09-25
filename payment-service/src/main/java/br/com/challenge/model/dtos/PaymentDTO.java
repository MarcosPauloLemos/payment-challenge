package br.com.challenge.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private Long clientId;
    private BuyerDTO buyer;
    private CardDTO cardDTO;
    private BigDecimal amount;
    private Integer typeCod;
}
