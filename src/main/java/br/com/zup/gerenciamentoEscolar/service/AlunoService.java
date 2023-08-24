package br.com.zup.gerenciamentoEscolar.service;

import br.com.zup.gerenciamentoEscolar.model.AlunoModel;
import br.com.zup.gerenciamentoEscolar.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    @Autowired
    AlunoRepository alunoRepository;


    public List<AlunoModel> listarTodasContas() {
        return alunoRepository.findAll();
    }

    public Optional<AlunoModel> buscarAlunoPeloId(Long id){
        return alunoRepository.findById(id);
    }

    public AlunoModel criarAluno(AlunoModel aluno){
        return alunoRepository.save(aluno);
    }

    public void deletarAluno(Long id){
        alunoRepository.deleteById(id);
    }
}
