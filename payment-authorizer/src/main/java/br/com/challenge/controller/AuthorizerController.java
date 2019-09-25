package br.com.challenge.controller;

import br.com.challenge.model.Card;
import br.com.challenge.service.interfaces.IBoletoService;
import br.com.challenge.service.interfaces.ICreditCardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Api(value = "Authorizer Payment End-Point",
        description = "Api simulates a payment authorizer. Generates boleto numbers and executes card transaction.")
@RestController
@RequestMapping("/authorizer")
public class AuthorizerController {

    @Autowired
    private IBoletoService boletoService;

    @Autowired
    private ICreditCardService creditCardService;

    @ApiOperation(value = "Get clientId, amount and payment Number and create a new boleto number")
    @GetMapping("/createBoleto")
    public ResponseEntity createBoleto(@RequestParam("clientId") @ApiParam(value = "ecommerce number") Long clientId,
                                       @RequestParam("amount") @ApiParam(value = "payment amount") BigDecimal amount,
                                       @RequestParam("paymentNumber") @ApiParam(value = "customer " +
                                               "internal control number")
                                                   Long paymentNumber){
        return ResponseEntity.ok(boletoService.createBoleto(clientId,amount,paymentNumber));
    }

    @ApiOperation(value = "Get card infos,  simulates card limit and allows payment")
    @PostMapping("/creditCardPayment")
    public ResponseEntity creditCardPayment(@RequestBody @ApiParam(value = "Credit Card purchase information")
                                                        Card card){
        return ResponseEntity.ok(creditCardService.registerCreditCardPayment(card));
    }
}
