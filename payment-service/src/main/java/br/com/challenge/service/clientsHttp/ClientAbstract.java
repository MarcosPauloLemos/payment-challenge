package br.com.challenge.service.clientsHttp;

import br.com.challenge.service.clientsHttp.interfaces.IClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public abstract class ClientAbstract implements IClient {

    private RestTemplate rest;
    private HttpHeaders headers;
    private HttpStatus status;
    protected ObjectMapper jsonMapper;

    public abstract String getServer();

    public ClientAbstract() {
        this.jsonMapper = new ObjectMapper();
        this.rest = new RestTemplate();
        this.headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Override
    public String get(String uri) {
        HttpEntity<String> requestEntity = new HttpEntity<>("", headers);
        ResponseEntity<String> responseEntity = rest.exchange(getServer() + uri, HttpMethod.GET,
                requestEntity, String.class);
        status = responseEntity.getStatusCode();
        return responseEntity.getBody();
    }

    @Override
    public String post(String uri, String json) {
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        ResponseEntity<String> responseEntity = rest.exchange(getServer() + uri,
                HttpMethod.POST, entity, String.class);
        status = responseEntity.getStatusCode();
        return responseEntity.getBody();
    }

    @Override
    public HttpStatus getStatus() {
        return this.status;
    }
}
