package br.com.challenge.service.clientsHttp.interfaces;

import br.com.challenge.model.Card;
import br.com.challenge.model.dtos.AuthorizerReturnDTO;

import java.math.BigDecimal;

public interface IPaymentAuthorizerClient {
    AuthorizerReturnDTO generateBoleto(Long clientId, BigDecimal amount, Long paymentNumber);

    AuthorizerReturnDTO authorizerCreditCard(Card card);
}
