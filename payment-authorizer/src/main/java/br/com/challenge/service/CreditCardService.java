package br.com.challenge.service;

import br.com.challenge.mapper.CreditCardMapper;
import br.com.challenge.model.Card;
import br.com.challenge.model.dtos.AuthorizerReturnDTO;
import br.com.challenge.model.enums.PaymentStatus;
import br.com.challenge.service.interfaces.ICreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Random;

@Service
public class CreditCardService implements ICreditCardService {

    private final Random random;

    public CreditCardService() {
        this.random = new Random();
    }

    @Autowired
    private CreditCardMapper boletoMapper;

    @Override
    public AuthorizerReturnDTO registerCreditCardPayment(Card card) {
        card = Optional.ofNullable(card).orElseThrow(() ->
                new ValidationException("Credit Card is invalid!"));
        card.setStatus(checkCreditLimitIsSufficient(card) ? PaymentStatus.APPROVED : PaymentStatus.REFUSED);
        registerCardTransaction(card);
        return AuthorizerReturnDTO.builder()
                .authorizerNumber(card.getId())
                .status(card.getStatus())
                .paymentRef(card.getNumber())
                .build();
    }

    public void registerCardTransaction(Card card) {
        boletoMapper.insert(card);
    }

    public Boolean checkCreditLimitIsSufficient(Card card) {
        return creditCardLimit().compareTo(card.getAmount()) > -1;
    }

    public BigDecimal creditCardLimit(){
        return BigDecimal.valueOf(Math.abs((random.nextDouble() * 10000) * random.nextDouble()));
    }
}
