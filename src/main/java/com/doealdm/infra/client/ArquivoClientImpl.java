package com.doealdm.infra.client;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.doealdm.client.arquivos.ArquivoClient;
import com.doealdm.client.arquivos.ArquivoModelResponse;

import io.undertow.server.handlers.form.FormData;

@Component
public class ArquivoClientImpl implements ArquivoClient {

    private final RestTemplate restTemplate;

    public ArquivoClientImpl(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Override
    public ArquivoModelResponse buscarArquivo(String arquivo) {
        String url = "http://127.0.0.1:8000/arquivos";
        ArquivoModelResponse response = restTemplate.getForObject(url, ArquivoModelResponse.class);
        return response;
    }

}
