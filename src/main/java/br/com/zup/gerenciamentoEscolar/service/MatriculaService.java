package br.com.zup.gerenciamentoEscolar.service;

import br.com.zup.gerenciamentoEscolar.model.AlunoModel;
import br.com.zup.gerenciamentoEscolar.model.CursoModel;
import br.com.zup.gerenciamentoEscolar.model.MatriculaModel;
import br.com.zup.gerenciamentoEscolar.repository.AlunoRepository;
import br.com.zup.gerenciamentoEscolar.repository.CursoRepository;
import br.com.zup.gerenciamentoEscolar.repository.MatriculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MatriculaService {

    @Autowired
    MatriculaRepository matriculaRepository;

    @Autowired
    AlunoRepository alunoRepository;

    @Autowired
    CursoRepository cursoRepository;

    public List<MatriculaModel> listarTodasMatriculas() {
        return matriculaRepository.findAll();
    }

    public Optional<MatriculaModel> buscarMatriculaPeloId(Long id) {
        return matriculaRepository.findById(id);
    }

    public MatriculaModel criarMatricula(MatriculaModel matricula) {
        AlunoModel aluno = alunoRepository.findById(matricula.getAluno().getId())
                .orElseThrow(() -> new NoSuchElementException("Aluno não encontrado"));
        CursoModel curso = cursoRepository.findById(matricula.getCurso().getId())
                .orElseThrow(() -> new NoSuchElementException("Curso não encontrado"));

        matricula.setAluno(aluno);
        matricula.setCurso(curso);
        return matriculaRepository.save(matricula);
    }

    public void deletarMatricula(Long id) {
        matriculaRepository.deleteById(id);
    }

    public MatriculaModel atualizaMatricula(MatriculaModel matricula) {
        return matriculaRepository.save(matricula);
    }
}
