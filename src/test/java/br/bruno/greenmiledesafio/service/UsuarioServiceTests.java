package br.bruno.greenmiledesafio.service;

import br.bruno.greenmiledesafio.exception.DadosInvalidosException;
import br.bruno.greenmiledesafio.exception.LoginExistenteException;
import br.bruno.greenmiledesafio.model.Usuario;
import br.bruno.greenmiledesafio.model.UsuarioDTO;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTests {

    @Autowired
    private UsuarioService usuarioService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void adicionarNovoUsuarioCorretamente() throws LoginExistenteException, DadosInvalidosException {
        UsuarioDTO usuario = new UsuarioDTO("Teste","teste_10","123", new ArrayList<>());

        Assert.assertNotNull(usuarioService.registerNewUser(usuario));
    }

    @Test
    public void erroAoAdicionarUsuarioComLoginJaExistente() throws LoginExistenteException, DadosInvalidosException {
        thrown.expect(LoginExistenteException.class);

        UsuarioDTO usuario = new UsuarioDTO("Teste","teste","123", new ArrayList<>());
        usuarioService.registerNewUser(usuario);

        UsuarioDTO usuario2 = new UsuarioDTO("Teste2","teste","123", new ArrayList<>());
        usuarioService.registerNewUser(usuario2);
    }

    @Test
    public void erroAoAdicionarUsuarioComDadosIncompletos() throws LoginExistenteException, DadosInvalidosException {
        thrown.expect(DadosInvalidosException.class);

        UsuarioDTO usuario = new UsuarioDTO();
        usuarioService.registerNewUser(usuario);
    }

    @Test
    public void localizarUsuarioCadastrado() throws LoginExistenteException, DadosInvalidosException {
        UsuarioDTO usuario = new UsuarioDTO("Teste","teste9","123", new ArrayList<>());
        Usuario usuarioSalvo = usuarioService.registerNewUser(usuario);

        Assert.assertTrue(usuarioService.findById(usuarioSalvo.getId()).isPresent());
    }

    @Test
    public void localizarTodosUsuariosCadastrados() throws LoginExistenteException, DadosInvalidosException {
        int oldSize = usuarioService.findAll().size();

        UsuarioDTO usuario = new UsuarioDTO("Teste","teste11","123", new ArrayList<>());
        UsuarioDTO usuario2 = new UsuarioDTO("Teste","teste12","123", new ArrayList<>());

        usuarioService.registerNewUser(usuario);
        usuarioService.registerNewUser(usuario2);

        Assert.assertEquals(usuarioService.findAll().size(), oldSize + 2);
    }

}
