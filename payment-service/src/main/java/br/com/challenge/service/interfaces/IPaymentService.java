package br.com.challenge.service.interfaces;

import br.com.challenge.model.Payment;
import br.com.challenge.model.dtos.AuthorizerReturnDTO;
import br.com.challenge.model.dtos.PaymentDTO;

import java.util.List;

public interface IPaymentService {

    AuthorizerReturnDTO paymentReceive(Payment payment);

    List<Payment> clientPayments(Long clientId);

    List<Payment> buyerPayments(Long cpf);

    Payment paymentForAuthorizerNumber(Long authorizerNumber);

    Payment mount(PaymentDTO payment);
}
