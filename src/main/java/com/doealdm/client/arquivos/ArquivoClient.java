package com.doealdm.client.arquivos;

import io.undertow.server.handlers.form.FormData;


public interface ArquivoClient {
    ArquivoModelResponse buscarArquivo(String arquivo);
}
