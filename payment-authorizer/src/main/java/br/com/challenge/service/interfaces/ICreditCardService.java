package br.com.challenge.service.interfaces;

import br.com.challenge.model.Card;
import br.com.challenge.model.dtos.AuthorizerReturnDTO;

public interface ICreditCardService {
    AuthorizerReturnDTO registerCreditCardPayment(Card card);
}
