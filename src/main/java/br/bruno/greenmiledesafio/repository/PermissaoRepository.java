package br.bruno.greenmiledesafio.repository;

import br.bruno.greenmiledesafio.model.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Long> {

    @Query("select p from Permissao p where descricao in (:permissoes)")
    List<Permissao> obterPermissoes(@Param("permissoes") List permissoes);

}
