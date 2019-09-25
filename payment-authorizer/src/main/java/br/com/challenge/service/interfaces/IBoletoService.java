package br.com.challenge.service.interfaces;

import br.com.challenge.model.dtos.AuthorizerReturnDTO;

import java.math.BigDecimal;

public interface IBoletoService {

    AuthorizerReturnDTO createBoleto(Long clientId, BigDecimal amount, Long paymentNumber);

}
