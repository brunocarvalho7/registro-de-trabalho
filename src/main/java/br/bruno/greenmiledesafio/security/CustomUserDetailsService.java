package br.bruno.greenmiledesafio.security;

import br.bruno.greenmiledesafio.model.Usuario;
import br.bruno.greenmiledesafio.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Usuario usuario = usuarioRepository.findByLogin(username);

        if(usuario == null)
            throw new UsernameNotFoundException("Usuário não encontrado");

        return usuario;
    }
}
