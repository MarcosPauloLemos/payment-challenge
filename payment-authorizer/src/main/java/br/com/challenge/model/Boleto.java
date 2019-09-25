package br.com.challenge.model;

import br.com.challenge.model.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Boleto {
    private Long id;
    private Long paymentNumber;
    private Long clientId;
    private Long number;
    private BigDecimal amount;
    private Integer statusCod;

    public PaymentStatus getStatus(){
        return PaymentStatus.toEnum(this.statusCod);
    }

    public void setStatus(PaymentStatus paymentStatus){
        this.statusCod = paymentStatus.getStatusCod();
    }
}
