package br.com.zup.gerenciamentoEscolar.service;

import br.com.zup.gerenciamentoEscolar.model.AlunoModel;
import br.com.zup.gerenciamentoEscolar.model.CursoModel;
import br.com.zup.gerenciamentoEscolar.model.ProfessorModel;
import br.com.zup.gerenciamentoEscolar.repository.CursoRepository;
import br.com.zup.gerenciamentoEscolar.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProfessorService {
    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private CursoRepository cursoRepository;

    public List<ProfessorModel> listarTodosProfessores() {
        return professorRepository.findAll();
    }

    public Optional<ProfessorModel> buscarProfessorPeloId(Long id) {
        return professorRepository.findById(id);
    }

    public ProfessorModel criarProfessor(ProfessorModel professor) {
        CursoModel curso = cursoRepository.findById(professor.getCurso().getId())
                .orElseThrow(() -> new NoSuchElementException("Curso não encontrado"));

        professor.setCurso(curso);
        return professorRepository.save(professor);
    }

    public void deletarProfessor(Long id) {
        professorRepository.deleteById(id);
    }
}
