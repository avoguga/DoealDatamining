package com.doealdm.domain;

import io.undertow.server.handlers.form.FormData;
import org.springframework.web.multipart.MultipartFile;

public class Arquivo {
    private String nomeArquivo;
    private MultipartFile arquivo;

    public String getNomeArquivo() {
        return this.nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public Arquivo nomeArquivo(String nomeArquivo) {
        this.setNomeArquivo(nomeArquivo);
        return this;
    }

    public MultipartFile getArquivo() {
        return this.arquivo;
    }

    public void setArquivo(MultipartFile pdf) {
        this.arquivo = pdf;
    }

    public Arquivo pdf(MultipartFile pdf) {
        this.setArquivo(pdf);
        return this;
    }

}

