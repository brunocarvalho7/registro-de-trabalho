package br.bruno.greenmiledesafio.service;

import br.bruno.greenmiledesafio.model.HorasTrabalhadas;
import br.bruno.greenmiledesafio.model.Usuario;
import br.bruno.greenmiledesafio.repository.HorasTrabalhadasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HorasTrabalhadasService {

    @Autowired
    private HorasTrabalhadasRepository horasTrabalhadasRepository;

    public List<HorasTrabalhadas> findHorasTrabalhadas(Long id) {
        return horasTrabalhadasRepository.findByUsuario(id);
    }

    public HorasTrabalhadas save(Usuario usuario, HorasTrabalhadas horasTrabalhadas) {
        horasTrabalhadas.setUsuario(usuario);

        return horasTrabalhadasRepository.save(horasTrabalhadas);
    }

    public Optional<HorasTrabalhadas> findById(Long id) {
        return horasTrabalhadasRepository.findById(id);
    }
}
