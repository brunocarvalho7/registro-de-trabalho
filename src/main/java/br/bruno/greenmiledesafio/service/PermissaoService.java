package br.bruno.greenmiledesafio.service;

import br.bruno.greenmiledesafio.model.Permissao;
import br.bruno.greenmiledesafio.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissaoService {

    @Autowired
    private PermissaoRepository permissaoRepository;

    public List<Permissao> obterPermissoes(List permissoes){
        return permissaoRepository.obterPermissoes(permissoes);
    }

}
