package br.bruno.greenmiledesafio.controller;

import br.bruno.greenmiledesafio.exception.DefaultException;
import br.bruno.greenmiledesafio.exception.ExceptionResponse;
import br.bruno.greenmiledesafio.exception.UsuarioNaoEncontradoException;
import br.bruno.greenmiledesafio.model.HorasTrabalhadas;
import br.bruno.greenmiledesafio.model.Usuario;
import br.bruno.greenmiledesafio.service.HorasTrabalhadasService;
import br.bruno.greenmiledesafio.service.UsuarioService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/horas")
@Api(value = "Horas trabalhadas", description = "REST API para operações de registro de horas trabalhadas",
        tags = {"Horas trabalhadas"})
public class HorasTrabalhadasController {

    @Autowired
    private HorasTrabalhadasService horasTrabalhadasService;

    @Autowired
    private UsuarioService usuarioService;


    @GetMapping("/{id}")
    @ApiOperation(value = "Retorna todos os registros de horas trabalhadas de um usuário específico")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Usuário não encontrado", response = ExceptionResponse.class),
    })
    public ResponseEntity<List<HorasTrabalhadas>> findHorasTrabalhadas(@ApiParam(name="Id",
                value="Código do usuário a ser mostrado as horas trabalhadas", required = true)
                                @PathVariable("id") Long id) throws UsuarioNaoEncontradoException {

            usuarioService.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));

        return ResponseEntity.ok(horasTrabalhadasService.findHorasTrabalhadas(id));
    }

    @PostMapping({"/{id}"})
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Registra as horas trabalhadas de um usuário", response = Usuario.class, code = 201
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = ExceptionResponse.class),
            @ApiResponse(code = 400, message = "Usuário não encontrado", response = ExceptionResponse.class),
            @ApiResponse(code = 403, message = "Access Denied", response = DefaultException.class),
            @ApiResponse(code = 500, message = "Token inválido", response = DefaultException.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name="Authorization", value = "Bearer token",
                    required = true, dataType = "String", paramType = "header")
    })
    public ResponseEntity<HorasTrabalhadas> saveHoraTrabalhada(@PathVariable("id") @ApiParam(name="Id",
            value="Código do usuário a ser inserido as horas trabalhadas", required = true) Long id,
                        @RequestBody HorasTrabalhadas horasTrabalhadas)
            throws UsuarioNaoEncontradoException{
        Usuario usuario = usuarioService.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));

        return ResponseEntity.ok(horasTrabalhadasService.save(usuario, horasTrabalhadas));
    }
}
