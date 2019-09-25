package br.com.challenge.mapper;

import br.com.challenge.model.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PaymentMapper {

    void insert(@Param("payment") Payment payment);

    void updateStatus(@Param("payment") Payment payment);

    List<Payment> clientPayments(@Param("clientId") Long clientId);

    List<Payment> buyerPayments(@Param("cpf") Long cpf);

    Payment paymentForAuthorizerNumber(@Param("authorizerNumber") Long authorizerNumber);

    void updateStatusBoletoAuthorizerNumber(@Param("payment") Payment payment);

    void updateStatusCardAuthorizerNumber(@Param("payment") Payment payment);
}
