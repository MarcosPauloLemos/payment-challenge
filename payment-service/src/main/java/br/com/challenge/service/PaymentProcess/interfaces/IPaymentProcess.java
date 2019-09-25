package br.com.challenge.service.PaymentProcess.interfaces;

import br.com.challenge.model.Payment;
import br.com.challenge.model.dtos.AuthorizerReturnDTO;

public interface IPaymentProcess {

    AuthorizerReturnDTO process();

    AuthorizerReturnDTO sendPaymentToAuthorizer(Payment payment);
}
