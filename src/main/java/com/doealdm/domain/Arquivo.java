package com.doealdm.domain;

public class Arquivo {
    private String arquivo;

    public String getNome() {
        return this.arquivo;
    }

    public Arquivo arquivo(String arquivo) {
        this.setArquivo(arquivo);
        return this;
    }

    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
    }

}

