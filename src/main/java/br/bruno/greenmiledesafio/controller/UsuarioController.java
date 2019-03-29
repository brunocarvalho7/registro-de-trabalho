package br.bruno.greenmiledesafio.controller;

import br.bruno.greenmiledesafio.exception.*;
import br.bruno.greenmiledesafio.model.Usuario;
import br.bruno.greenmiledesafio.model.UsuarioDTO;
import br.bruno.greenmiledesafio.service.UsuarioService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("v1/usuarios")
@Api(value = "Usuário", description = "REST API para operações com o Usuário", tags = {"Usuário"})
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("")
    @ApiOperation(value = "Retorna uma lista com todos os usuários cadastrados",
    response = Usuario[].class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Usuario[].class)
    })
    public ResponseEntity<List<Usuario>> findAll(){
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Retorna todos os dados de um usuário especifico")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Usuário não encontrado", response = ExceptionResponse.class),
    })
    public ResponseEntity<Usuario> find(@ApiParam(name="Id", value="Código do usuário a ser pesquisado",
                required = true) @PathVariable("id") Long id) throws UsuarioNaoEncontradoException {
        Usuario usuario = usuarioService.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));

        return ResponseEntity.ok(usuario);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Cadastra um novo usuário", response = Usuario.class, code = 201)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Login já existente", response = ExceptionResponse.class),
            @ApiResponse(code = 403, message = "Access Denied", response = DefaultException.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name="Authorization", value = "Bearer token",
            required = true, dataType = "String", paramType = "header")
    })
    public ResponseEntity<Usuario> save(@RequestBody
                                      @ApiParam(name="Usuario", value="Usuario a ser cadastrado", required = true)
                                              UsuarioDTO usuarioDTO) throws LoginExistenteException, DadosInvalidosException {

        Usuario usuarioSalvo = usuarioService.registerNewUser(usuarioDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(usuarioSalvo.getId()).toUri();

        return ResponseEntity.created(location).body(usuarioSalvo);
    }

}
