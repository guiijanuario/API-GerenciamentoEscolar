package br.com.zup.gerenciamentoEscolar.controller;

import br.com.zup.gerenciamentoEscolar.dto.MatriculaDTO;
import br.com.zup.gerenciamentoEscolar.enums.TipoLogEvento;
import br.com.zup.gerenciamentoEscolar.model.CursoModel;
import br.com.zup.gerenciamentoEscolar.model.MatriculaModel;
import br.com.zup.gerenciamentoEscolar.service.CursoService;
import br.com.zup.gerenciamentoEscolar.service.LogEventosService;
import br.com.zup.gerenciamentoEscolar.service.MatriculaService;
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
@Tag(name = "Feature - Matriculas")
public class MatriculaController {

    @Autowired
    MatriculaService matriculaService;

    @Autowired
    LogEventosService logEventosService;

    @Autowired
    CursoService cursoService;

    @GetMapping("/matriculas")
    @Operation(summary = " : Lista todas as matrículas", method = "GET")
    public ResponseEntity<List<MatriculaModel>> listarTodasMatriculas() {
        logEventosService.gerarLogListarAll(TipoLogEvento.LISTOU_MATRICULAS);
        return ResponseEntity.ok(matriculaService.listarTodasMatriculas());
    }

    @GetMapping("/matriculas/{id}")
    @Operation(summary = " : Lista uma matrícula pelo ID", method = "GET")
    public ResponseEntity<?> listarMatriculaId(@PathVariable Long id) {
        Optional<MatriculaModel> matriculaEncontrada = matriculaService.buscarMatriculaPeloId(id);

        if (matriculaEncontrada.isEmpty()) {
            MatriculaModel matriculaNull = new MatriculaModel();
            logEventosService.gerarLogBuscaDePeloId(matriculaNull, TipoLogEvento.MATRICULA_NAO_ENCONTRADA);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Matrícula não encontrada, tente novamente!");
        }

        MatriculaModel matricula = matriculaEncontrada.orElseThrow(() -> new NoSuchElementException("Matrícula não encontrada"));
        logEventosService.gerarLogBuscaDePeloId(matricula, TipoLogEvento.LISTOU_MATRICULA);
        return ResponseEntity.ok(matriculaEncontrada.get());
    }

    @PostMapping("/matriculas")
    @Operation(summary = " : Cadastra uma matricula", method = "POST")
    public ResponseEntity<MatriculaModel> cadastrarMatricula(@RequestBody MatriculaModel matriculaModel) {
        MatriculaModel novaMatricula = matriculaService.criarMatricula(matriculaModel);
        logEventosService.gerarLogCadastroRealizado(novaMatricula, TipoLogEvento.MATRICULA_CADASTRADA);
        return new ResponseEntity<>(novaMatricula, HttpStatus.CREATED);
    }

    @DeleteMapping("/matriculas/{id}")
    @Operation(summary = " : Deleta um curso pelo ID", method = "DELETE")
    public void deletarMatricula(@PathVariable Long id) {
        Optional<MatriculaModel> matriculaEncontrada = matriculaService.buscarMatriculaPeloId(id);
        MatriculaModel matricula = matriculaEncontrada.orElseThrow(() -> new NoSuchElementException("Matrícula não encontrada"));
        logEventosService.gerarLogDeleteRealizado(matricula, TipoLogEvento.MATRICULA_DELETADA);
        matriculaService.deletarMatricula(id);
    }

    @PutMapping("/matriculas/{id}")
    @Operation(summary = " : Altera o curso de uma matrícula", method = "PUT")
    public ResponseEntity<?> alterarCursoMatricula(@PathVariable Long id, @RequestBody MatriculaDTO matriculaDTO) {
        Optional<MatriculaModel> matriculaExistente = matriculaService.buscarMatriculaPeloId(id);

        if (matriculaExistente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Matrícula não encontrada, tente novamente!");
        }

        MatriculaModel matricula = matriculaExistente.get();
        CursoModel novoCurso = cursoService.buscarCursoPeloId(matriculaDTO.novoCursoId())
                .orElseThrow(() -> new NoSuchElementException("Curso não encontrado"));

        matricula.setCurso(novoCurso);

        MatriculaModel matriculaAtualizada = matriculaService.atualizaMatricula(matricula);
        logEventosService.gerarLogAtualizacaoRealizada(matriculaAtualizada, TipoLogEvento.MATRICULA_ATUALIZADA);

        return ResponseEntity.ok(matriculaAtualizada);
    }
}
