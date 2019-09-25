package br.com.challenge.model;


import br.com.challenge.model.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Long paymentNumber;
    @JsonIgnore
    private Integer statusCod;

    public PaymentStatus getStatus(){
        return PaymentStatus.toEnum(this.statusCod);
    }

    public void setStatus(PaymentStatus paymentStatus){
        this.statusCod = paymentStatus.getStatusCod();
    }


}
