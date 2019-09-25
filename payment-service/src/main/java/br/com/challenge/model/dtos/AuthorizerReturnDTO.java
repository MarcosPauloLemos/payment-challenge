package br.com.challenge.model.dtos;

import br.com.challenge.model.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizerReturnDTO {
    private Long authorizerNumber;
    private PaymentStatus status;
    private Long paymentRef;
}
