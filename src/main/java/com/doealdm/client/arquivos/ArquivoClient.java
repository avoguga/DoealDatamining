package com.doealdm.client.arquivos;

import com.doealdm.infra.client.ResponseApiPython;
import io.undertow.server.handlers.form.FormData;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface ArquivoClient {
    List<String> buscarNomeArquivo();
    ResponseApiPython uploadArquivo(MultipartFile arquivo) throws IOException;
    public void uploadArquivoToLocal(MultipartFile file);
}
