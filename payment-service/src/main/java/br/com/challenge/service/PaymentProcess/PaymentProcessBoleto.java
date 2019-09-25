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
public class PaymentProcessBoleto extends PaymentProcessAbstract {
    protected PaymentProcessBoleto(PaymentAuthorizerClient paymentAuthorizerClient, PaymentMapper paymentMapper) {
        super(paymentAuthorizerClient, paymentMapper);
    }

    @Override
    public Boolean mustProcess(PaymentType type) {
        return type.equals(PaymentType.BOLETO);
    }


    @Override
    public AuthorizerReturnDTO sendPaymentToAuthorizer(Payment payment){
        AuthorizerReturnDTO authorizerReturnDTO = paymentAuthorizerClient.generateBoleto(payment.getClientId(),
                    payment.getAmount(),payment.getId());

        authorizerReturnDTO = Optional.ofNullable(authorizerReturnDTO).orElseThrow(() ->
                new ValidationException(""));

        payment.setStatus(authorizerReturnDTO.getStatus());
        payment.setAuthorizerNumber(authorizerReturnDTO.getAuthorizerNumber());
        payment.setBoletoNumber(authorizerReturnDTO.getPaymentRef());
        paymentMapper.updateStatusBoletoAuthorizerNumber(payment);
        return authorizerReturnDTO;
    }
}
