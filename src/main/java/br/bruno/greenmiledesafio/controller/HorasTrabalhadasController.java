package br.bruno.greenmiledesafio.controller;

import br.bruno.greenmiledesafio.exception.UsuarioNaoEncontradoException;
import br.bruno.greenmiledesafio.model.HorasTrabalhadas;
import br.bruno.greenmiledesafio.model.Usuario;
import br.bruno.greenmiledesafio.service.HorasTrabalhadasService;
import br.bruno.greenmiledesafio.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("horas")
public class HorasTrabalhadasController {

    @Autowired
    private HorasTrabalhadasService horasTrabalhadasService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<List<HorasTrabalhadas>> findHorasTrabalhadas(@PathVariable("id") Long id){
        return ResponseEntity.ok(horasTrabalhadasService.findHorasTrabalhadas(id));
    }

    @PostMapping({"/{id}"})
    public ResponseEntity<HorasTrabalhadas> saveHoraTrabalhada(@PathVariable("id") Long id,
                                                               @RequestBody HorasTrabalhadas horasTrabalhadas)
            throws UsuarioNaoEncontradoException{
        Usuario usuario = usuarioService.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));

        return ResponseEntity.ok(horasTrabalhadasService.save(usuario, horasTrabalhadas));
    }
}
