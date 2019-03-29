package br.bruno.greenmiledesafio.controller;

import br.bruno.greenmiledesafio.exception.LoginExistenteException;
import br.bruno.greenmiledesafio.model.LoginDTO;
import br.bruno.greenmiledesafio.model.Usuario;
import br.bruno.greenmiledesafio.model.UsuarioDTO;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsuarioControllerTests {

    @Autowired
    private UsuarioController usuarioController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void aoListarTodosOsUsuariosDeveRetonarStatus200() throws LoginExistenteException {
        ResponseEntity<String> response = restTemplate.getForEntity("/v1/usuarios/",String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void aoListarOsDadosDeUmUsuarioDeveRetonarStatus200() throws LoginExistenteException {
        ResponseEntity<String> response = restTemplate.getForEntity("/v1/usuarios/{id}",String.class,1);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void aoListarOsDadosDeUmUsuarioInexistenteDeveRetonarStatus400() throws LoginExistenteException {
        ResponseEntity<Usuario> response = restTemplate.getForEntity("/v1/usuarios/{id}",Usuario.class,10000);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void aoListarOsDadosDeUmUsuarioComParametroInvalidoDeveRetonarStatus400() throws LoginExistenteException {
        ResponseEntity<Usuario> response = restTemplate.getForEntity("/v1/usuarios/{id}",Usuario.class,10000);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void aoTentarSalvarUmNovoUsuarioSemEstaLogadoDeveRetornarStatus403(){
        UsuarioDTO usuarioDTO = new UsuarioDTO("Abacaxi", "abacaxi", "123", new ArrayList<>());
        ResponseEntity<Usuario> response = restTemplate.postForEntity("/v1/usuarios", usuarioDTO, Usuario.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void aoTentarSalvarUmNovoUsuarioEstandoLogadoDeveRetornarStatus201(){
        UsuarioDTO usuario = new UsuarioDTO("Melao", "melao", "melao", new ArrayList<>());

        ResponseEntity<Usuario> response = restTemplate.exchange("/v1/usuarios",
                HttpMethod.POST, new HttpEntity<>(usuario, getHeadersAuthorization()), Usuario.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void aoTentarSalvarUmNovoUsuarioComLoginJaCadastradoEstandoLogadoDeveRetornarStatus400(){
        UsuarioDTO usuario = new UsuarioDTO("Tangerina", "tangerina", "tangerina", new ArrayList<>());

        ResponseEntity<Usuario> response = restTemplate.exchange("/v1/usuarios",
                HttpMethod.POST, new HttpEntity<>(usuario, getHeadersAuthorization()), Usuario.class);

        UsuarioDTO usuario2 = new UsuarioDTO("Abacaxi", "tangerina", "abacaxi", new ArrayList<>());

        ResponseEntity<Usuario> response2 = restTemplate.exchange("/v1/usuarios",
                HttpMethod.POST, new HttpEntity<>(usuario2, getHeadersAuthorization()), Usuario.class);

        Assertions.assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    private HttpHeaders getHeadersAuthorization(){
        LoginDTO loginDTO = new LoginDTO("admin", "123");

        ResponseEntity<String> response = restTemplate.postForEntity("/v1/login", loginDTO, String.class);

        HttpHeaders requestHeader = new HttpHeaders();
        requestHeader.add("Authorization", response.getBody());
        requestHeader.add("Content-Type", "application/json");

        return requestHeader;
    }
}
