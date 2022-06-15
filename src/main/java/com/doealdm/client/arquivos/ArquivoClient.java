package com.doealdm.client.arquivos;

import io.undertow.server.handlers.form.FormData;

import java.util.List;


public interface ArquivoClient {
    List<String> buscarArquivo();
}
