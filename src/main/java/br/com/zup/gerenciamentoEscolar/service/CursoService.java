package br.com.zup.gerenciamentoEscolar.service;

import br.com.zup.gerenciamentoEscolar.model.CursoModel;
import br.com.zup.gerenciamentoEscolar.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoService {

    @Autowired
    CursoRepository cursoRepository;

    public List<CursoModel> listarTodosCursos() {
        return cursoRepository.findAll();
    }

    public Optional<CursoModel> buscarCursoPeloId(Long id) {
        return cursoRepository.findById(id);
    }

    public CursoModel criarCurso(CursoModel curso) {
        return cursoRepository.save(curso);
    }

    public void deletarCurso(Long id) {
        cursoRepository.deleteById(id);
    }
}
