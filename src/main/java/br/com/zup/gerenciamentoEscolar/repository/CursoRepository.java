package br.com.zup.gerenciamentoEscolar.repository;

import br.com.zup.gerenciamentoEscolar.model.CursoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<CursoModel, Long> {
}
