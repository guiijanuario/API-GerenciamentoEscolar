package br.com.zup.gerenciamentoEscolar.repository;

import br.com.zup.gerenciamentoEscolar.model.ProfessorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends JpaRepository<ProfessorModel, Long> {
}
