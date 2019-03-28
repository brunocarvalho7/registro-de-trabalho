package br.bruno.greenmiledesafio.controller;

import br.bruno.greenmiledesafio.exception.DadosInvalidosException;
import br.bruno.greenmiledesafio.exception.LoginExistenteException;
import br.bruno.greenmiledesafio.exception.UsuarioNaoEncontradoException;
import br.bruno.greenmiledesafio.model.Usuario;
import br.bruno.greenmiledesafio.model.UsuarioDTO;
import br.bruno.greenmiledesafio.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping({"","/"})
    public ResponseEntity<List<Usuario>> findAll(){
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @PostMapping({"","/"})
    public ResponseEntity<?> save(@RequestBody UsuarioDTO usuarioDTO, BindingResult result) throws LoginExistenteException, DadosInvalidosException {
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body(result);
        }

        Usuario usuarioSalvo = usuarioService.registerNewUser(usuarioDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(usuarioSalvo.getId()).toUri();

        return ResponseEntity.created(location).body(usuarioSalvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> find(@PathVariable("id") Long id) throws UsuarioNaoEncontradoException {
        Usuario usuario = usuarioService.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));

        return ResponseEntity.ok(usuario);
    }

}
