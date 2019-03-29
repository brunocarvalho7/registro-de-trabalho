package br.bruno.greenmiledesafio.service;

import br.bruno.greenmiledesafio.exception.DadosInvalidosException;
import br.bruno.greenmiledesafio.exception.LoginExistenteException;
import br.bruno.greenmiledesafio.model.HorasTrabalhadas;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HorasTrabalhadasServiceTests {

    @Autowired
    private HorasTrabalhadasService horasTrabalhadasService;

    @Autowired
    private UsuarioService usuarioService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void encontrarHorasTrabalhadasCorretamente() throws LoginExistenteException, DadosInvalidosException {
        Usuario usuario = usuarioService.registerNewUser(new UsuarioDTO(
                        "Jão", "jao01", "jao01", new ArrayList<>()));

        horasTrabalhadasService.save(usuario, new HorasTrabalhadas(LocalDate.of(2019,3,28),
                LocalTime.of(0,30)));

        horasTrabalhadasService.save(usuario, new HorasTrabalhadas(LocalDate.of(2019,3,28),
                LocalTime.of(0,40)));

        horasTrabalhadasService.save(usuario, new HorasTrabalhadas(LocalDate.of(2019,3,28),
                LocalTime.of(0,50)));

        Assert.assertEquals(horasTrabalhadasService.findHorasTrabalhadas(usuario.getId()).size(),3);
    }

    @Test
    public void salvarHoraTrabalhadasCorretamente() throws LoginExistenteException, DadosInvalidosException {
        Usuario usuario = usuarioService.registerNewUser(new UsuarioDTO(
                "Jão2", "jao02", "jao02", new ArrayList<>()));

        Assert.assertNotNull( horasTrabalhadasService.save(usuario, new HorasTrabalhadas(
                    LocalDate.of(2019,3,28), LocalTime.of(0,50))));
    }
}
