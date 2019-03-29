package br.bruno.greenmiledesafio.controller;

import br.bruno.greenmiledesafio.model.HorasTrabalhadas;
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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HorasTrabalhadasControllerTests {

    @Autowired
    private HorasTrabalhadasController horasTrabalhadasController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void aoTentarAdicionarEstandoLogadoDeveRetornarCodigo201(){
        //---------> Criar o usuário
        UsuarioDTO usuario = new UsuarioDTO("Toin", "toin01", "toin01", new ArrayList<>());

        ResponseEntity<Usuario> responseUsuario = restTemplate.exchange("/v1/usuarios",
                HttpMethod.POST, new HttpEntity<>(usuario, getHeadersAdminAuthorization()), Usuario.class);

        //---------> Criar a hora trabalhada

        HorasTrabalhadas horaTrabalhada = new HorasTrabalhadas(
                LocalDate.of(2019,2,10), LocalTime.of(1,30));

        //---------> Realizar a requisição POST para /v1/horas/{id}

        ResponseEntity<HorasTrabalhadas> responseHT = restTemplate.exchange("/v1/horas/{id}",
                HttpMethod.POST, new HttpEntity<>(horaTrabalhada, getHeadersAuthorization(usuario)),
                HorasTrabalhadas.class, responseUsuario.getBody().getId());

        Assertions.assertThat(responseHT.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void aoTentarAdicionarHorasParaUsuarioInexistenteEstandoLogadoDeveRetornarCodigo400(){
        //---------> Criar a hora trabalhada

        HorasTrabalhadas horaTrabalhada = new HorasTrabalhadas(
                LocalDate.of(2019,2,10), LocalTime.of(1,30));

        //---------> Realizar a requisição POST para /v1/horas/{id} com um ID inválido

        ResponseEntity<HorasTrabalhadas> responseHT = restTemplate.exchange("/v1/horas/{id}",
                HttpMethod.POST, new HttpEntity<>(horaTrabalhada, getHeadersAdminAuthorization()),
                HorasTrabalhadas.class, 500000L);

        Assertions.assertThat(responseHT.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void aoTentarAdicionarEstandoDeslogadoDeveRetornarCodigo403(){
        //---------> Criar o usuário
        UsuarioDTO usuario = new UsuarioDTO("Toin", "toin02", "toin02", new ArrayList<>());

        ResponseEntity<Usuario> responseUsuario = restTemplate.exchange("/v1/usuarios",
                HttpMethod.POST, new HttpEntity<>(usuario, getHeadersAdminAuthorization()), Usuario.class);

        //---------> Criar a hora trabalhada

        HorasTrabalhadas horaTrabalhada = new HorasTrabalhadas(
                LocalDate.of(2019,2,10), LocalTime.of(1,30));

        //---------> Realizar a requisição POST para /v1/horas/{id}

        ResponseEntity<HorasTrabalhadas> responseHT = restTemplate.exchange("/v1/horas/{id}",
                HttpMethod.POST, new HttpEntity<>(horaTrabalhada, getHeadersWithoutAuthorization()),
                HorasTrabalhadas.class, responseUsuario.getBody().getId());

        Assertions.assertThat(responseHT.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void aoTentarAdicionarComTokenInvalidoDeveRetornarCodigo403(){
        //---------> Criar o usuário
        UsuarioDTO usuario = new UsuarioDTO("Toin", "toin03", "toin03", new ArrayList<>());

        ResponseEntity<Usuario> responseUsuario = restTemplate.exchange("/v1/usuarios",
                HttpMethod.POST, new HttpEntity<>(usuario, getHeadersAdminAuthorization()), Usuario.class);

        //---------> Criar a hora trabalhada

        HorasTrabalhadas horaTrabalhada = new HorasTrabalhadas(
                LocalDate.of(2019,2,10), LocalTime.of(1,30));

        //---------> Realizar a requisição POST para /v1/horas/{id}

        ResponseEntity<HorasTrabalhadas> responseHT = restTemplate.exchange("/v1/horas/{id}",
                HttpMethod.POST, new HttpEntity<>(horaTrabalhada, getHeadersWithWrongAuthorization(usuario)),
                HorasTrabalhadas.class, responseUsuario.getBody().getId());

        Assertions.assertThat(responseHT.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void aoTentarLocalizarRegistroDeUmaHoraTrabalhadaComIdValidoDeveRetornarCodigo200(){
        //---------> Criar o usuário
        UsuarioDTO usuario = new UsuarioDTO("Toin", "toin04", "toin04", new ArrayList<>());

        ResponseEntity<Usuario> responseUsuario = restTemplate.exchange("/v1/usuarios",
                HttpMethod.POST, new HttpEntity<>(usuario, getHeadersAdminAuthorization()), Usuario.class);

        //---------> Criar a hora trabalhada

        HorasTrabalhadas horaTrabalhada = new HorasTrabalhadas(
                LocalDate.of(2019,2,10), LocalTime.of(1,30));

        //---------> Realizar a requisição POST para /v1/horas/{id} para gravar a hora trabalhada

        ResponseEntity<HorasTrabalhadas> responseHT = restTemplate.exchange("/v1/horas/{id}",
                HttpMethod.POST, new HttpEntity<>(horaTrabalhada, getHeadersAuthorization(usuario)),
                HorasTrabalhadas.class, responseUsuario.getBody().getId());

        //---------> E finalmente realizar uma requisição GET para /v1/horas/registro/{id}
        ResponseEntity<HorasTrabalhadas> responseHT2 = restTemplate.exchange("/v1/horas/registro/{id}",
                HttpMethod.GET, new HttpEntity<>(null, getHeadersAuthorization(usuario)),
                HorasTrabalhadas.class, responseHT.getBody().getId());

        Assertions.assertThat(responseHT2.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void aoTentarLocalizarRegistroDeUmaHoraTrabalhadaComIdInvalidoDeveRetornarCodigo400(){
        //---------> realizar uma requisição GET para /v1/horas/registro/{id}
        ResponseEntity<HorasTrabalhadas> response = restTemplate.exchange("/v1/horas/registro/{id}",
                HttpMethod.GET, new HttpEntity<>(null, getHeadersAdminAuthorization()),
                HorasTrabalhadas.class, 2000000L);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void aoTentarMostrarTodasAsHorasTrabalhadasDeUmUsuarioValidoDeveRetornarCodigo200(){
        //---------> Criar o usuário
        UsuarioDTO usuario = new UsuarioDTO("Toin", "toin05", "toin05", new ArrayList<>());

        ResponseEntity<Usuario> responseUsuario = restTemplate.exchange("/v1/usuarios",
                HttpMethod.POST, new HttpEntity<>(usuario, getHeadersAdminAuthorization()), Usuario.class);

        //---------> Criar a hora trabalhada

        HorasTrabalhadas horaTrabalhada = new HorasTrabalhadas(
                LocalDate.of(2019,2,10), LocalTime.of(1,30));

        //---------> Realizar a requisição POST para /v1/horas/{id} para gravar a hora trabalhada

        ResponseEntity<HorasTrabalhadas> responseHT = restTemplate.exchange("/v1/horas/{id}",
                HttpMethod.POST, new HttpEntity<>(horaTrabalhada, getHeadersAuthorization(usuario)),
                HorasTrabalhadas.class, responseUsuario.getBody().getId());

        //---------> Criar outra hora trabalhada

        HorasTrabalhadas horaTrabalhada2 = new HorasTrabalhadas(
                LocalDate.of(2019,2,10), LocalTime.of(1,30));

        //---------> Realizar outra requisição POST para /v1/horas/{id} para gravar a hora trabalhada

        responseHT = restTemplate.exchange("/v1/horas/{id}",
                HttpMethod.POST, new HttpEntity<>(horaTrabalhada2, getHeadersAuthorization(usuario)),
                HorasTrabalhadas.class, responseUsuario.getBody().getId());

        //---------> E finalmente realizar uma requisição GET para /v1/horas/{id}
        ResponseEntity<List<HorasTrabalhadas>> responseHT2 = restTemplate.exchange("/v1/horas/{id}",
                HttpMethod.GET, new HttpEntity<>(null, getHeadersAuthorization(usuario)),
                new ParameterizedTypeReference<List<HorasTrabalhadas>>() {}, responseUsuario.getBody().getId());

        Assertions.assertThat(responseHT2.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseHT2.getBody().size()).isEqualTo(2);
    }

    @Test
    public void aoTentarMostrarTodasAsHorasTrabalhadasDeUmUsuarioValidoDeveRetornarCodigo400(){
        //---------> realizar uma requisição GET para /v1/horas/{id}
        ResponseEntity<HorasTrabalhadas> response = restTemplate.exchange("/v1/horas/{id}",
                HttpMethod.GET, new HttpEntity<>(null, getHeadersAdminAuthorization()),
                HorasTrabalhadas.class, 2000000L);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    private HttpHeaders getHeadersAdminAuthorization(){
        LoginDTO loginDTO = new LoginDTO("admin", "123");

        ResponseEntity<String> response = restTemplate.postForEntity("/v1/login", loginDTO, String.class);

        HttpHeaders requestHeader = new HttpHeaders();
        requestHeader.add("Authorization", response.getBody());
        requestHeader.add("Content-Type", "application/json");

        return requestHeader;
    }

    private HttpHeaders getHeadersAuthorization(UsuarioDTO usuario){
        LoginDTO loginDTO = new LoginDTO(usuario.getUsername(), usuario.getPassword());

        ResponseEntity<String> response = restTemplate.postForEntity("/v1/login", loginDTO, String.class);

        HttpHeaders requestHeader = new HttpHeaders();
        requestHeader.add("Authorization", response.getBody());
        requestHeader.add("Content-Type", "application/json");

        return requestHeader;
    }

    private HttpHeaders getHeadersWithWrongAuthorization(UsuarioDTO usuarioDTO){
        LoginDTO loginDTO = new LoginDTO(usuarioDTO.getUsername(), usuarioDTO.getPassword());

        ResponseEntity<String> response = restTemplate.postForEntity("/v1/login", loginDTO, String.class);

        HttpHeaders requestHeader = new HttpHeaders();
        requestHeader.add("Authorization", "aaaaaaaa.aaaaa");
        requestHeader.add("Content-Type", "application/json");

        return requestHeader;
    }

    private HttpHeaders getHeadersWithoutAuthorization(){
        HttpHeaders requestHeader = new HttpHeaders();
        requestHeader.add("Content-Type", "application/json");

        return requestHeader;
    }
}
