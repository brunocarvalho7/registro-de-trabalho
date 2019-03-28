package br.bruno.greenmiledesafio.repository;

import br.bruno.greenmiledesafio.model.HorasTrabalhadas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HorasTrabalhadasRepository extends JpaRepository<HorasTrabalhadas, Long> {

    @Query("select h from HorasTrabalhadas h where usuario_id=:usu")
    List<HorasTrabalhadas> findByUsuario(@Param("usu") Long id);
}
