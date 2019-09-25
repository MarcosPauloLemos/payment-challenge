package br.com.challenge.service;


import br.com.challenge.mapper.BoletoMapper;
import br.com.challenge.model.Boleto;
import br.com.challenge.model.dtos.AuthorizerReturnDTO;
import br.com.challenge.model.enums.PaymentStatus;
import br.com.challenge.service.interfaces.IBoletoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.util.Random;

@Service
public class BoletoService implements IBoletoService {

    @Autowired
    private BoletoMapper boletoMapper;

    @Override
    public AuthorizerReturnDTO createBoleto(Long clientId, BigDecimal amount, Long paymentNumber) {
        if(clientId == null || clientId.compareTo(0L) < 1)
            throw new ValidationException("id client entered is invalid!");

        if(amount.compareTo(BigDecimal.valueOf(0.1)) < 0)
            throw new ValidationException("invalid value, minimum 1 cent!");

        if(paymentNumber.compareTo(0L) < 1)
            throw new ValidationException("invalid paymentNumber!");

        Boleto boleto = generateBoleto(clientId, amount, paymentNumber);
        return AuthorizerReturnDTO.builder()
                .authorizerNumber(boleto.getId())
                .status(boleto.getStatus())
                .paymentRef(boleto.getNumber())
                .build();
    }

    public Boleto generateBoleto(Long clientId, BigDecimal amount, Long paymentNumber) {
        Boleto boleto = Boleto.builder()
                .number(generateBoletoNumber())
                .clientId(clientId)
                .amount(amount)
                .paymentNumber(paymentNumber)
                .statusCod(PaymentStatus.WAITING_PAY.getStatusCod())
                .build();
        boletoMapper.insert(boleto);
        return boleto;
    }

    public Long generateBoletoNumber() {
        return Math.abs(new Random().nextLong());
    }
}
