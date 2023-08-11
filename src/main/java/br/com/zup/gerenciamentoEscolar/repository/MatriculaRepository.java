package br.com.zup.gerenciamentoEscolar.repository;

import br.com.zup.gerenciamentoEscolar.model.MatriculaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatriculaRepository extends JpaRepository<MatriculaModel, Long> {
}
