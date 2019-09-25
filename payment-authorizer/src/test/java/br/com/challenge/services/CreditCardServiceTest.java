package br.com.challenge.services;

import br.com.challenge.model.Card;
import br.com.challenge.model.dtos.AuthorizerReturnDTO;
import br.com.challenge.model.enums.PaymentStatus;
import br.com.challenge.service.CreditCardService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreditCardServiceTest {

    private static final Long       CARD_ID     = 1L      ;
    private static final Long       CLIENT_ID   = 1L      ;
    private static final String     HOLDER_NAME = "HOLDER";
    private static final Long       NUMBER      = 5432167L;
    private static final LocalDate  DT_EXP      = LocalDate.parse("2023-09-30");
    private static final Integer    CVV         = 321;
    private static final BigDecimal LIMIT       = BigDecimal.valueOf(1200.00);

    private CreditCardService creditCardService;

    @Before
    public void setUp() {
        creditCardService = Mockito.mock(CreditCardService.class);
        Mockito.when(creditCardService.creditCardLimit()).thenReturn(LIMIT);

    }

    @Test
    public void registerCreditCardPaymentAccept() {
        Card cardMock =   Card.builder()
                .id(CARD_ID)
                .clientId(CLIENT_ID)
                .holderName(HOLDER_NAME)
                .number(NUMBER)
                .expirationDate(DT_EXP)
                .cvv(CVV)
                .amount(BigDecimal.valueOf(1000.00))
                .paymentNumber(1L)
                .build();

        Mockito.when(creditCardService.registerCreditCardPayment(cardMock)).thenCallRealMethod();
        Mockito.when(creditCardService.checkCreditLimitIsSufficient(cardMock)).thenCallRealMethod();
        AuthorizerReturnDTO authorizerReturnDTO = creditCardService.registerCreditCardPayment(cardMock);

        Assert.assertEquals(authorizerReturnDTO.getAuthorizerNumber(),cardMock.getId());
        Assert.assertEquals(authorizerReturnDTO.getStatus(), PaymentStatus.APPROVED);
        Assert.assertEquals(authorizerReturnDTO.getPaymentRef(),NUMBER);
    }

    @Test
    public void registerCreditCardPaymentRefused() {
        Card cardMock =   Card.builder()
                .id(CARD_ID)
                .clientId(CLIENT_ID)
                .holderName(HOLDER_NAME)
                .number(NUMBER)
                .expirationDate(DT_EXP)
                .cvv(CVV)
                .amount(BigDecimal.valueOf(1300.00))
                .paymentNumber(1L)
                .build();

        Mockito.when(creditCardService.registerCreditCardPayment(cardMock)).thenCallRealMethod();
        Mockito.when(creditCardService.checkCreditLimitIsSufficient(cardMock)).thenCallRealMethod();
        AuthorizerReturnDTO authorizerReturnDTO = creditCardService.registerCreditCardPayment(cardMock);

        Assert.assertEquals(authorizerReturnDTO.getAuthorizerNumber(),cardMock.getId());
        Assert.assertEquals(authorizerReturnDTO.getStatus(), PaymentStatus.REFUSED);
        Assert.assertEquals(authorizerReturnDTO.getPaymentRef(),NUMBER);
    }
}
