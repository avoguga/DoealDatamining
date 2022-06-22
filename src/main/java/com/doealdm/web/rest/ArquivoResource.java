package com.doealdm.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.doealdm.client.arquivos.ArquivoClient;
import com.doealdm.client.arquivos.ArquivoModelResponse;
import com.doealdm.domain.Arquivo;
import com.doealdm.service.ArquivoService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.undertow.server.handlers.form.FormData;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ArquivoResource {

    @Autowired
    private final ArquivoService arquivoService;
    private ArquivoClient arquivoClient;

    public ArquivoResource(ArquivoService arquivoService) {
        this.arquivoService = arquivoService;
    }

    @GetMapping("/arquivos")
    public ResponseEntity<List<String>> buscarNomeArquivo() {
         return ResponseEntity.ok().body(this.arquivoService.buscarNomeArquivo());
    }

    @PostMapping("/arquivos")
    public ResponseEntity<String> buscarArquivo(@RequestParam("files")MultipartFile file) throws IOException  {
        this.arquivoClient.uploadArquivoToLocal(file);
        return ResponseEntity.ok().body(file.getOriginalFilename());
    }

}
