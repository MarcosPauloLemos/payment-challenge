package br.com.challenge.model;

import br.com.challenge.model.enums.PaymentStatus;
import br.com.challenge.model.enums.PaymentType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Payment {
    private Long id;
    private Long clientId;
    private Buyer buyer;
    private Card card;
    private Long boletoNumber;
    private Long cardNumber;
    private BigDecimal amount;
    private Long authorizerNumber;
    private Integer typeCod;
    private Integer statusCod;

    public PaymentType getType(){
        return PaymentType.toEnum(this.typeCod);
    }

    public void setType(PaymentType paymentType){
        paymentType = Optional.ofNullable(paymentType)
                .orElseThrow(() -> new ValidationException("Invalid paymentType!"));
        this.typeCod = paymentType.getTypeCod();
    }

    public PaymentStatus getStatus(){
        return PaymentStatus.toEnum(this.statusCod);
    }

    public void setStatus(PaymentStatus paymentStatus){
        paymentStatus = Optional.ofNullable(paymentStatus)
                .orElseThrow(() -> new IllegalArgumentException("Invalid paymentStatus!"));
        this.statusCod = paymentStatus.getStatusCod();
    }
}
