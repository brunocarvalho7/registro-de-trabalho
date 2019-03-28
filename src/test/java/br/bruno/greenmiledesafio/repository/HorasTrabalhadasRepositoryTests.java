package br.bruno.greenmiledesafio.repository;

import br.bruno.greenmiledesafio.model.HorasTrabalhadas;
import br.bruno.greenmiledesafio.model.Usuario;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalTime;

@RunWith(SpringRunner.class)
@DataJpaTest
public class HorasTrabalhadasRepositoryTests {

    @Autowired
    private HorasTrabalhadasRepository horasTrabalhadasRepository;

    @Autowired UsuarioRepository usuarioRepository;
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void adicionarHoraTrabalhadaCorretamente(){
        Usuario usuario = usuarioRepository.findByLogin("admin");

        HorasTrabalhadas hora = new HorasTrabalhadas(LocalDate.of(2019,03,28),
                LocalTime.of(06,40), usuario);

        Assert.assertNotNull(horasTrabalhadasRepository.save(hora));
    }

}
