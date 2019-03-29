package br.bruno.greenmiledesafio.controller;

import br.bruno.greenmiledesafio.model.LoginDTO;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void aoLogarComCredenciasCorretasDeveRetornarUmaAuthorizationMaisUmCodigo200(){
        //---------> Criar o objeto com os dados de login
        LoginDTO loginDTO = new LoginDTO("admin", "123");

        //---------> Realizar a requisição POST para /v1/login para ser autenticado e gerado o token
        ResponseEntity<String> response = restTemplate.exchange("/v1/login", HttpMethod.POST,
                new HttpEntity<>(loginDTO, getHeadersWithoutAuthorization()), String.class);

        //---------> Deve-se ter o statusCode = 200 e um header Authorization
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getHeaders().get("Authorization")).isNotNull();
    }

    @Test
    public void aoLogarComCredenciasInorretasDeveRetornarCodigo400(){
        //---------> Criar o objeto com os dados de login
        LoginDTO loginDTO = new LoginDTO("zezim", "123");

        //---------> Realizar a requisição POST para /v1/login para se tentar autenticar
        ResponseEntity<String> response = restTemplate.exchange("/v1/login", HttpMethod.POST,
                new HttpEntity<>(loginDTO, getHeadersWithoutAuthorization()), String.class);

        //---------> Deve-se ter o statusCode = 200 e um header Authorization
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    private HttpHeaders getHeadersWithoutAuthorization(){
        HttpHeaders requestHeader = new HttpHeaders();
        requestHeader.add("Content-Type", "application/json");

        return requestHeader;
    }

}
