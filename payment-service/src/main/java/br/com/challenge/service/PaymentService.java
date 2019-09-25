package br.com.challenge.service;

import br.com.challenge.exceptions.BadValidationException;
import br.com.challenge.mapper.BuyerMapper;
import br.com.challenge.mapper.PaymentMapper;
import br.com.challenge.model.Buyer;
import br.com.challenge.model.Card;
import br.com.challenge.model.Payment;
import br.com.challenge.model.dtos.AuthorizerReturnDTO;
import br.com.challenge.model.dtos.PaymentDTO;
import br.com.challenge.model.enums.PaymentType;
import br.com.challenge.service.PaymentProcess.PaymentProcessFactory;
import br.com.challenge.service.interfaces.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService implements IPaymentService {

    @Autowired
    private BuyerMapper buyerMapper;

    @Autowired
    private PaymentProcessFactory paymentProcessFactory;

    @Autowired
    private PaymentMapper paymentMapper;

    @Override
    public AuthorizerReturnDTO paymentReceive(Payment payment) {
        validatePayment(payment);
        loadEntities(payment);
        return paymentProcessFactory.defineStrategy(payment).process();
    }

    @Override
    public List<Payment> clientPayments(Long clientId) {
        if(clientId == null || clientId.compareTo(0L) < 1)
            throw new BadValidationException("invalid clientId!");

        return paymentMapper.clientPayments(clientId);
    }

    @Override
    public List<Payment> buyerPayments(Long cpf) {
        if(cpf == null || cpf.compareTo(0L) < 1)
            throw new BadValidationException("invalid cpf!");

        return paymentMapper.buyerPayments(cpf);
    }

    @Override
    public Payment paymentForAuthorizerNumber(Long authorizerNumber) {
        if(authorizerNumber == null || authorizerNumber.compareTo(0L) < 1)
            throw new BadValidationException("invalid authorizerNumber!");

        return paymentMapper.paymentForAuthorizerNumber(authorizerNumber);
    }

    @Override
    public Payment mount(PaymentDTO paymentDTO) {
        paymentDTO = Optional.ofNullable(paymentDTO).orElseThrow(()
                -> new BadValidationException("Invalid Payment!"));

        Buyer buyer = Buyer.builder()
                .name(paymentDTO.getBuyer().getName())
                .email(paymentDTO.getBuyer().getEmail())
                .cpf(paymentDTO.getBuyer().getCpf())
                .build();

        Card card = null;
        if(paymentDTO.getCardDTO() != null){
            card = Card.builder()
                    .holderName(paymentDTO.getCardDTO().getHolderName())
                    .number(paymentDTO.getCardDTO().getNumber())
                    .expirationDt(paymentDTO.getCardDTO().getExpirationDate())
                    .cvv(paymentDTO.getCardDTO().getCvv())
                    .build();
        }

        return Payment.builder()
                .clientId(paymentDTO.getClientId())
                .buyer(buyer)
                .card(card)
                .amount(paymentDTO.getAmount())
                .boletoNumber(null)
                .authorizerNumber(null)
                .typeCod(paymentDTO.getTypeCod())
                .build();
    }

    private void loadEntities(Payment payment) {
        Buyer buyer = buyerMapper.findByCpf(payment.getBuyer().getCpf());
        if(buyer == null){
            buyer = payment.getBuyer();
            buyerMapper.insert(buyer);
        }
        payment.setBuyer(buyer);
        if(payment.getCard() != null)
            payment.getCard().setAmount(payment.getAmount());
    }

    private void validatePayment(Payment payment) {
        payment = Optional.ofNullable(payment).orElseThrow(() ->
                new BadValidationException("Invalid Payment!"));

        if(payment.getAmount().compareTo(BigDecimal.valueOf(0.1)) < 0)
            throw new BadValidationException("invalid value, minimum 1 cent!");

        if(payment.getClientId() == null || payment.getClientId().compareTo(0L) < 1)
            throw new BadValidationException("id client entered is invalid!");

        if(payment.getBuyer() == null)
            throw new BadValidationException("Buyer is not valid");

        if(payment.getBuyer().getCpf().compareTo(0L) < 1)
            throw new BadValidationException("Invalid buyer CPF!");

        if(payment.getBuyer().getName().isEmpty())
            throw new BadValidationException("Invalid buyer name!");

        if(payment.getBuyer().getEmail().isEmpty())
            throw new BadValidationException("Invalid buyer email!");

        if(payment.getType().equals(PaymentType.CREDIT_CARD)){
            if(payment.getCard() == null)
                throw new BadValidationException("credit card is invalid!");

            if(payment.getCard().getHolderName().isEmpty())
                throw new BadValidationException("invalid holder name!");

            if(payment.getCard().getNumber().compareTo(0L) < 1)
                throw new BadValidationException("invalid card number!");

            if(payment.getCard().getExpirationDt() == null ||
                    payment.getCard().getExpirationDt().compareTo(LocalDate.now()) < 0)
                throw new BadValidationException("Card expiration date is invalid!");

            if(payment.getCard().getCvv().compareTo(0) < 1)
                throw new BadValidationException("CVV is invalid!");
        }
    }
}
