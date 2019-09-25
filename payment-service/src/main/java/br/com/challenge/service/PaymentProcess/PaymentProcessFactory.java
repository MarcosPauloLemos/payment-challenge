package br.com.challenge.service.PaymentProcess;

import br.com.challenge.model.Payment;
import br.com.challenge.service.PaymentProcess.interfaces.IPaymentProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentProcessFactory {

    @Autowired
    private List<IPaymentProcess> paymentProcessStrategy;

    public IPaymentProcess defineStrategy(Payment payment){
        for (IPaymentProcess iPaymentProcess : paymentProcessStrategy){
            PaymentProcessAbstract aPayProcess = (PaymentProcessAbstract) iPaymentProcess;
            if(aPayProcess.mustProcess(payment.getType())){
                ((PaymentProcessAbstract) iPaymentProcess).payment = payment;
                return iPaymentProcess;
            }
        }
        throw new IllegalArgumentException("Invalid paymentType!");
    }
}
