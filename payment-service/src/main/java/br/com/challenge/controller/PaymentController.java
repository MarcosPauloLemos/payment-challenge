package br.com.challenge.controller;

import br.com.challenge.model.dtos.PaymentDTO;
import br.com.challenge.service.interfaces.IPaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "Service Payment End-Point",
        description = "Manages information about purchases and on track for authorizer to validate payment.")
@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private IPaymentService paymentService;

    @ApiOperation(value = "receive payment information and return approval status")
    @PostMapping("/receive")
    public ResponseEntity paymentReceive(@RequestBody @ApiParam(value = "payment data") PaymentDTO payment){
        return ResponseEntity.ok(paymentService.paymentReceive(paymentService.mount(payment)));
    }

    @ApiOperation(value = "List customer payments.")
    @GetMapping("/payments/client/{client-id}")
    public ResponseEntity paymentsClient(@PathVariable("client-id") @ApiParam(value = "Ecommerce id") Long clientId){
        return ResponseEntity.ok(paymentService.clientPayments(clientId));
    }

    @ApiOperation(value = "List Buyer Payments")
    @GetMapping("/payments/buyer/{cpf}")
    public ResponseEntity paymentsBuyer(@PathVariable("cpf") @ApiParam(value = "Buyer CPF") Long cpf){
        return ResponseEntity.ok(paymentService.buyerPayments(cpf));
    }
}
