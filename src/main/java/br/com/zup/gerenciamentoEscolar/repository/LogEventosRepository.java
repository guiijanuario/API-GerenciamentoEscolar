package br.com.zup.gerenciamentoEscolar.repository;

import br.com.zup.gerenciamentoEscolar.model.LogEventosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogEventosRepository extends JpaRepository<LogEventosModel, Long> {
}
