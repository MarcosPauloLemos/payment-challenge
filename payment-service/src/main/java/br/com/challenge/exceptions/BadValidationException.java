package br.com.challenge.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ValidationException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadValidationException extends ValidationException {
    public BadValidationException(String messege) {
        super(messege);
    }
}
