package br.bruno.greenmiledesafio.service;

import br.bruno.greenmiledesafio.exception.DadosInvalidosException;
import br.bruno.greenmiledesafio.exception.LoginExistenteException;
import br.bruno.greenmiledesafio.model.Usuario;
import br.bruno.greenmiledesafio.model.UsuarioDTO;
import br.bruno.greenmiledesafio.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private PermissaoService permissaoService;

    @Autowired
    private UsuarioRepository usuarioRepo;

    public Usuario registerNewUser(UsuarioDTO usuario) throws LoginExistenteException, DadosInvalidosException {
        if(usuarioRepo.findByLogin(usuario.getUsername()) != null)
            throw new LoginExistenteException(usuario.getUsername());

        if(!usuario.isValid())
            throw new DadosInvalidosException();

        Usuario newUser = new Usuario();
        newUser.setNome(usuario.getNome());
        newUser.setLogin(usuario.getUsername());
        newUser.setSenha(new BCryptPasswordEncoder().encode(usuario.getPassword()));
        newUser.setAtivo(true);
        newUser.setPermissoes(permissaoService.obterPermissoes(usuario.getPermissoes()));

        return usuarioRepo.save(newUser);
    }

    public List<Usuario> findAll(){
        return usuarioRepo.findAll();
    }

    public Optional<Usuario> findById(Long id){
        return usuarioRepo.findById(id);
    }
}
