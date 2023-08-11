package br.com.zup.gerenciamentoEscolar.controller;

import br.com.zup.gerenciamentoEscolar.dto.AlunoDTO;
import br.com.zup.gerenciamentoEscolar.enums.TipoLogEvento;
import br.com.zup.gerenciamentoEscolar.model.AlunoModel;
import br.com.zup.gerenciamentoEscolar.service.AlunoService;
import br.com.zup.gerenciamentoEscolar.service.LogEventosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/gerenciamentoEscolar", produces = {"application/json"})
@Tag(name = "Feature - Alunos")
public class AlunoController {

    @Autowired
    AlunoService alunoService;

    @Autowired
    LogEventosService logEventosService;

    @GetMapping("/alunos")
    @Operation(summary = " : Lista todas os alunos", method = "GET")
    public ResponseEntity<List<AlunoModel>> listarTodosAlunos(){
        logEventosService.gerarLogListarAll(TipoLogEvento.LISTOU_ALUNOS);
        return ResponseEntity.ok(alunoService.listarTodasContas());
    }

    @GetMapping(path = "/alunos/{id}")
    @Operation(summary = " : Lista um aluno pelo ID", method = "GET")
    public ResponseEntity<?> listarAlunoId(@PathVariable Long id){
        Optional<AlunoModel> alunoEncontrado = alunoService.buscarAlunoPeloId(id);

        if(alunoEncontrado.isEmpty()){
            AlunoModel alunoNull = new AlunoModel();
            logEventosService.gerarLogBuscaDePeloId(alunoNull, TipoLogEvento.ALUNO_NAO_ENCONTRADO);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aluno não encontrado tente novamente!");
        }

        AlunoModel aluno = alunoEncontrado.orElseThrow(() -> new NoSuchElementException("Aluno não encontrado"));

        logEventosService.gerarLogBuscaDePeloId(aluno, TipoLogEvento.LISTOU_ALUNO);

        return ResponseEntity.ok(alunoEncontrado.get());
    }

    @PostMapping("/alunos")
    public ResponseEntity<AlunoDTO> cadastrarAluno(@RequestBody AlunoModel alunoModel){
        AlunoModel novoAluno = alunoService.criarAluno(alunoModel);
        logEventosService.gerarLogCadastroRealizado(novoAluno, TipoLogEvento.ALUNO_CADASTRADO);

        AlunoDTO alunoDTO = new AlunoDTO(novoAluno.getNome(), novoAluno.getIdade(), novoAluno.getEmail());
        return new ResponseEntity<>(alunoDTO, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/alunos/{id}")
    public void deletarAluno(@PathVariable Long id){
        Optional<AlunoModel> alunoEncontrado = alunoService.buscarAlunoPeloId(id);
        AlunoModel aluno = alunoEncontrado.orElseThrow(() -> new NoSuchElementException("Aluno não encontrado"));
        logEventosService.gerarLogDeleteRealizado(aluno, TipoLogEvento.ALUNO_DELETADO);
        alunoService.deletarAluno(id);
    }

}
