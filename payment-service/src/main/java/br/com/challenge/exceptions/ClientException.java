package br.com.challenge.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ClientException  extends RuntimeException{
    public ClientException(String messege){
        super(messege);
    }
}
