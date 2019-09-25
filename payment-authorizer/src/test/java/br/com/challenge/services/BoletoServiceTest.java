package br.com.challenge.services;

import br.com.challenge.mapper.BoletoMapper;
import br.com.challenge.model.Boleto;
import br.com.challenge.model.dtos.AuthorizerReturnDTO;
import br.com.challenge.model.enums.PaymentStatus;
import br.com.challenge.service.BoletoService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class BoletoServiceTest {

    private static final Long       CLIENT_ID     = 1L;
    private static final BigDecimal BOLETO_AMOUNT = BigDecimal.TEN;
    private static final Long       PAY_NUMBER    = 1L;
    private static final Long       BOLETO_NUMBER = 112345678L;

    private BoletoService boletoService;
    private BoletoMapper boletoMapper;

    @Before
    public void setUp() {
        boletoService = Mockito.mock(BoletoService.class);
        boletoMapper = Mockito.mock(BoletoMapper.class);
    }

    @Test
    public void createBoleto(){
        Boleto boletoMock = Boleto.builder()
                .id(1L)
                .paymentNumber(PAY_NUMBER)
                .clientId(CLIENT_ID)
                .number(BOLETO_NUMBER)
                .amount(BOLETO_AMOUNT)
                .statusCod(PaymentStatus.WAITING_PAY.getStatusCod())
                .build();

        Mockito.when(boletoService.createBoleto(CLIENT_ID,BOLETO_AMOUNT,PAY_NUMBER)).thenCallRealMethod();
        Mockito.when(boletoService.generateBoleto(CLIENT_ID,BOLETO_AMOUNT,PAY_NUMBER)).thenReturn(boletoMock);
        Mockito.when(boletoMapper.findBy(1L)).thenReturn(boletoMock);

        AuthorizerReturnDTO authorizerReturnDTO = boletoService.createBoleto(CLIENT_ID,BOLETO_AMOUNT,PAY_NUMBER);
        Boleto boleto = boletoMapper.findBy(authorizerReturnDTO.getAuthorizerNumber());

        assertEquals(boleto.getId(),authorizerReturnDTO.getAuthorizerNumber());
        assertEquals(boleto.getAmount(),BOLETO_AMOUNT);
        assertEquals(boleto.getClientId(),CLIENT_ID);
        assertEquals(boleto.getNumber(),authorizerReturnDTO.getPaymentRef());
    }
}
