package br.bruno.greenmiledesafio.repository;

import br.bruno.greenmiledesafio.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByLogin(String login);

}
