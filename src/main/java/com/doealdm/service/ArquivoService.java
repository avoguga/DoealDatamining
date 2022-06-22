package com.doealdm.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.doealdm.client.arquivos.ArquivoClient;

import java.io.IOException;
import java.util.List;

@Service
public class ArquivoService {
    
    @Autowired
    private final ArquivoClient arquivoClient;

    public ArquivoService(ArquivoClient arquivoClient) {
        this.arquivoClient = arquivoClient;
    }

    public List<String> buscarNomeArquivo() {
        return this.arquivoClient.buscarNomeArquivo();
    }

    public void uploadArquivoToLocal(MultipartFile file) throws IOException {
        byte[] data = file.getBytes();
    }
}
