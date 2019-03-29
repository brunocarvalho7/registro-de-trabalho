package br.bruno.greenmiledesafio.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URI;
import java.net.URISyntaxException;

@Controller
public class HomeController {

    private final static String urlPrincipal = "/swagger-ui.html";

    @GetMapping({"", "/"})
    public ResponseEntity<Object> getHomePage() throws URISyntaxException {
        URI apiDocs = new URI(urlPrincipal);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(apiDocs);

        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }

}
