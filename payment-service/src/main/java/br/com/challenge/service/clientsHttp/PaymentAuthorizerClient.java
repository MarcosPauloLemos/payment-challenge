package br.com.challenge.service.clientsHttp;

import br.com.challenge.exceptions.ClientException;
import br.com.challenge.model.Card;
import br.com.challenge.model.dtos.AuthorizerReturnDTO;
import br.com.challenge.service.clientsHttp.interfaces.IPaymentAuthorizerClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

@Slf4j
@Service
public class PaymentAuthorizerClient extends ClientAbstract implements IPaymentAuthorizerClient {

    @Value("${payment-authorizer.uri}")
    private String server;

    @Override
    public String getServer() {
        return server;
    }

    @Override
    public AuthorizerReturnDTO generateBoleto(Long clientId, BigDecimal amount, Long paymentNumber){
        try {
            return jsonMapper.readValue(get("/createBoleto?clientId="+clientId
                            +"&amount="+amount
                            +"&paymentNumber="+paymentNumber)
                    ,AuthorizerReturnDTO.class);
        } catch (IOException e){
            if(log.isInfoEnabled()) log.info(e.getMessage());
            throw new ClientException("communication failure with authorizer!");
        }
    }

    @Override
    public AuthorizerReturnDTO authorizerCreditCard(Card card) {
        try {
            return jsonMapper.readValue(post("/creditCardPayment",
                    jsonMapper.writeValueAsString(card))
                    ,AuthorizerReturnDTO.class);
        } catch (IOException e){
            if(log.isInfoEnabled()) log.info(e.getMessage());
            throw new ClientException("communication failure with authorizer!");
        }
    }
}
