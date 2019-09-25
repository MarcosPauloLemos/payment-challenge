package br.com.challenge.model;


import br.com.challenge.model.enums.PaymentStatus;
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
    private Long id;
    private Long clientId;
    private String holderName;
    private Long number;
    private LocalDate expirationDate;
    private Integer cvv;
    private BigDecimal amount;
    private Long paymentNumber;
    private Integer statusCod;

    public PaymentStatus getStatus(){
        return PaymentStatus.toEnum(this.statusCod);
    }

    public void setStatus(PaymentStatus paymentStatus){
        this.statusCod = paymentStatus.getStatusCod();
    }


}
