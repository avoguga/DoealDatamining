package com.doealdm.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doealdm.client.arquivos.ArquivoModelResponse;
import com.doealdm.service.ArquivoService;

import io.undertow.server.handlers.form.FormData;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ArquivoResource {

    private final ArquivoService arquivoService;

    public ArquivoResource(ArquivoService arquivoService) {
        this.arquivoService = arquivoService;
    }

    @GetMapping("/arquivos")
    public ResponseEntity<List<String>> buscarArquivo() {
         return ResponseEntity.ok().body(this.arquivoService.buscarArquivo());
    }
}
