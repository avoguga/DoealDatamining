package com.doealdm.service;

import org.springframework.stereotype.Service;

import com.doealdm.client.arquivos.ArquivoClient;
import com.doealdm.client.arquivos.ArquivoModelResponse;

import io.undertow.server.handlers.form.FormData;

@Service
public class ArquivoService {

    private final ArquivoClient arquivoClient;

    public ArquivoService(ArquivoClient arquivoClient) {
        this.arquivoClient = arquivoClient;
    }

    public ArquivoModelResponse buscarArquivo(String arquivo) {
        return this.arquivoClient.buscarArquivo(arquivo);
    }
}
