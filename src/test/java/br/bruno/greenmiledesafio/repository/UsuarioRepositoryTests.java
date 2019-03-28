package br.bruno.greenmiledesafio.repository;

import br.bruno.greenmiledesafio.model.Usuario;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UsuarioRepositoryTests {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void adicionarNovoUsuarioCorretamente(){
        Usuario usuario = new Usuario("Teste","teste","123", new ArrayList<>());

        Assert.assertNotNull(usuarioRepository.save(usuario));
    }

    @Test
    public void erroAoAdicionarUsuarioSemCamposObrigatorios(){
        thrown.expect(ConstraintViolationException.class);
        Usuario usuario = new Usuario();

        usuarioRepository.save(usuario);
    }

    @Test
    public void erroAoAdicionarUsuarioComLoginJaExistente(){
        thrown.expect(DataIntegrityViolationException.class);

        Usuario usuario = new Usuario("Teste","teste","123", new ArrayList<>());
        usuarioRepository.save(usuario);

        Usuario usuario2 = new Usuario("Teste2","teste","123", new ArrayList<>());
        usuarioRepository.save(usuario2);
    }

    @Test
    public void localizarUsuarioCadastrado(){
        Usuario usuario = new Usuario("Teste","teste","123", new ArrayList<>());
        usuarioRepository.save(usuario);

        Assert.assertNotNull(usuarioRepository.findByLogin(usuario.getUsername()));
    }

    @Test
    public void erroAoLocalizarUsuarioNaoCadastrado(){
        Usuario usuario = new Usuario("Teste","teste","123", new ArrayList<>());
        usuarioRepository.save(usuario);

        Assert.assertNull(usuarioRepository.findByLogin("ABACAXI"));
    }

}
