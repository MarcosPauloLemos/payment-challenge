package br.com.challenge.service.PaymentProcess;

import br.com.challenge.mapper.PaymentMapper;
import br.com.challenge.model.Payment;
import br.com.challenge.model.dtos.AuthorizerReturnDTO;
import br.com.challenge.model.enums.PaymentType;
import br.com.challenge.service.clientsHttp.PaymentAuthorizerClient;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.Optional;

@Service
public class PaymentProcessCard extends PaymentProcessAbstract {
    protected PaymentProcessCard(PaymentAuthorizerClient paymentAuthorizerClient, PaymentMapper paymentMapper) {
        super(paymentAuthorizerClient, paymentMapper);
    }

    @Override
    public Boolean mustProcess(PaymentType type) {
        return type.equals(PaymentType.CREDIT_CARD);
    }


    @Override
    public AuthorizerReturnDTO sendPaymentToAuthorizer(Payment payment) {
        payment.getCard().setPaymentNumber(payment.getId());
        payment.getCard().setClientId(payment.getClientId());
        AuthorizerReturnDTO authorizerReturnDTO = paymentAuthorizerClient.authorizerCreditCard(payment.getCard());
        authorizerReturnDTO = Optional.ofNullable(authorizerReturnDTO).orElseThrow(() ->
                new ValidationException(""));
        payment.setStatus(authorizerReturnDTO.getStatus());
        payment.setAuthorizerNumber(authorizerReturnDTO.getAuthorizerNumber());
        payment.setCardNumber(authorizerReturnDTO.getPaymentRef());
        paymentMapper.updateStatusCardAuthorizerNumber(payment);
        return authorizerReturnDTO;
    }
}
