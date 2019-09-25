package br.com.challenge.service.PaymentProcess;

import br.com.challenge.mapper.PaymentMapper;
import br.com.challenge.model.Payment;
import br.com.challenge.model.dtos.AuthorizerReturnDTO;
import br.com.challenge.model.enums.PaymentStatus;
import br.com.challenge.model.enums.PaymentType;
import br.com.challenge.service.PaymentProcess.interfaces.IPaymentProcess;
import br.com.challenge.service.clientsHttp.PaymentAuthorizerClient;


public abstract class PaymentProcessAbstract implements IPaymentProcess {
    protected final PaymentAuthorizerClient paymentAuthorizerClient;
    protected final PaymentMapper paymentMapper;
    protected Payment payment;

    protected PaymentProcessAbstract(PaymentAuthorizerClient paymentAuthorizerClient, PaymentMapper paymentMapper) {
        this.paymentAuthorizerClient = paymentAuthorizerClient;
        this.paymentMapper = paymentMapper;
    }

    abstract Boolean mustProcess(PaymentType type);

    @Override
    public AuthorizerReturnDTO process(){
        payment.setStatus(PaymentStatus.WAITING_PROCESS);
        paymentMapper.insert(payment);
        return sendPaymentToAuthorizer(payment);
    }
}
