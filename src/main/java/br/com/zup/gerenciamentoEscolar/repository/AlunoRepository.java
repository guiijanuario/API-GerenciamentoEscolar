package br.com.zup.gerenciamentoEscolar.repository;

import br.com.zup.gerenciamentoEscolar.model.AlunoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepository extends JpaRepository<AlunoModel, Long> {
}
