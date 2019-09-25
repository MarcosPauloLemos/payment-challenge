package br.com.challenge.service.clientsHttp.interfaces;

import org.springframework.http.HttpStatus;

public interface IClient {
    String get(String uri);

    String post(String uri, String json);

    HttpStatus getStatus();
}
