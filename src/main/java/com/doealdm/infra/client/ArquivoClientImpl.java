package com.doealdm.infra.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import com.doealdm.client.arquivos.ArquivoClient;

@Component
public class ArquivoClientImpl implements ArquivoClient {

    private final RestTemplate restTemplate;

    public ArquivoClientImpl(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    private String url = "http://127.0.0.1:8000/arquivos";

    public List<String> buscarNomeArquivo( ) {
        List<String> response = restTemplate.getForObject(url, ArrayList.class);
        return response;
    }

    @Override
    public ResponseApiPython uploadArquivo(MultipartFile arquivo) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("files", new MultipartInputStreamFileResource());

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseApiPython response = restTemplate.postForObject(url, request, ResponseApiPython.class);
        return response;
    }

    @Override
    public void uploadArquivoToLocal(MultipartFile file) {
        try {
            byte[] data = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}