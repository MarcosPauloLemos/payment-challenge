package br.com.challenge.service;

import br.com.challenge.model.Payment;
import br.com.challenge.model.dtos.BuyerDTO;
import br.com.challenge.model.dtos.CardDTO;
import br.com.challenge.model.dtos.PaymentDTO;
import br.com.challenge.model.enums.PaymentType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentServiceTest {

    private static final Long        PAY_CLIENT_ID    = 1L;
    private static final BigDecimal  PAY_AMOUNT       = BigDecimal.valueOf(1400.00);

    private static final String      CARD_HOLDER_NAME = "HOLDER";
    private static final Long        CARD_NUMBER      = 5432167L;
    private static final LocalDate   CARD_DT_EXP      = LocalDate.parse("2023-09-30");
    private static final Integer     CARD_CVV         = 321;

    private static final String      BUY_NAME         = "MARCOS P S LEMOS";
    private static final String      BUY_EMAIL        = "marcospaulolemos0@gmail.com";
    private static final Long        BUY_CPF          = 4405806900L;

    private PaymentService paymentService;
    @Before
    public void setUp(){
        paymentService = Mockito.mock(PaymentService.class);
    }

    @Test
    public void mountBoletoTest(){
        BuyerDTO buyerDTO = BuyerDTO.builder()
                .name(BUY_NAME)
                .email(BUY_EMAIL)
                .cpf(BUY_CPF)
                .build();

        PaymentDTO paymentDTOMock = PaymentDTO.builder()
                .clientId(PAY_CLIENT_ID)
                .amount(PAY_AMOUNT)
                .buyer(buyerDTO)
                .typeCod(PaymentType.BOLETO.getTypeCod())
                .build();

        Mockito.when(paymentService.mount(paymentDTOMock)).thenCallRealMethod();
        Payment payment = paymentService.mount(paymentDTOMock);
        Assert.assertNotNull(payment);
        Assert.assertEquals(payment.getClientId(),paymentDTOMock.getClientId());
        Assert.assertEquals(payment.getAmount(),paymentDTOMock.getAmount());
        Assert.assertEquals(payment.getType().getTypeCod(),paymentDTOMock.getTypeCod());

        Assert.assertEquals(payment.getBuyer().getName(),paymentDTOMock.getBuyer().getName());
        Assert.assertEquals(payment.getBuyer().getEmail(),paymentDTOMock.getBuyer().getEmail());
        Assert.assertEquals(payment.getBuyer().getCpf(),paymentDTOMock.getBuyer().getCpf());
    }

    @Test
    public void mountCardTest(){
        BuyerDTO buyerDTO = BuyerDTO.builder()
                .name(BUY_NAME)
                .email(BUY_EMAIL)
                .cpf(BUY_CPF)
                .build();

        CardDTO cardDTO = CardDTO.builder()
                .holderName(CARD_HOLDER_NAME)
                .number(CARD_NUMBER)
                .expirationDate(CARD_DT_EXP)
                .cvv(CARD_CVV)
                .build();

        PaymentDTO paymentDTOMock = PaymentDTO.builder()
                .clientId(PAY_CLIENT_ID)
                .amount(PAY_AMOUNT)
                .buyer(buyerDTO)
                .cardDTO(cardDTO)
                .typeCod(PaymentType.CREDIT_CARD.getTypeCod())
                .build();

        Mockito.when(paymentService.mount(paymentDTOMock)).thenCallRealMethod();
        Payment payment = paymentService.mount(paymentDTOMock);
        Assert.assertNotNull(payment);
        Assert.assertEquals(payment.getClientId(),paymentDTOMock.getClientId());
        Assert.assertEquals(payment.getAmount(),paymentDTOMock.getAmount());
        Assert.assertEquals(payment.getType().getTypeCod(),paymentDTOMock.getTypeCod());

        Assert.assertEquals(payment.getBuyer().getName(),paymentDTOMock.getBuyer().getName());
        Assert.assertEquals(payment.getBuyer().getEmail(),paymentDTOMock.getBuyer().getEmail());
        Assert.assertEquals(payment.getBuyer().getCpf(),paymentDTOMock.getBuyer().getCpf());

        Assert.assertEquals(payment.getCard().getHolderName(),paymentDTOMock.getCardDTO().getHolderName());
        Assert.assertEquals(payment.getCard().getNumber(),paymentDTOMock.getCardDTO().getNumber());
        Assert.assertEquals(payment.getCard().getExpirationDt(),paymentDTOMock.getCardDTO().getExpirationDate());
        Assert.assertEquals(payment.getCard().getCvv(),paymentDTOMock.getCardDTO().getCvv());
    }

}
