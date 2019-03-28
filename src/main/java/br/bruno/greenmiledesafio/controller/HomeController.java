package br.bruno.greenmiledesafio.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;

@Controller
public class HomeController {

    @GetMapping({"", "/"})
    public ResponseEntity<Object> getHomePage(HttpServletResponse response) throws URISyntaxException {
        URI apiDocs = new URI("/swagger-ui.html");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(apiDocs);
        response.setHeader("Location", "/swagger-ui.html");

        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }

}
