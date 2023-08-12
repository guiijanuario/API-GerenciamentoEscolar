package br.com.zup.gerenciamentoEscolar.controller;

import br.com.zup.gerenciamentoEscolar.dto.AlunoDTO;
import br.com.zup.gerenciamentoEscolar.dto.ProfessorDTO;
import br.com.zup.gerenciamentoEscolar.enums.TipoLogEvento;
import br.com.zup.gerenciamentoEscolar.model.AlunoModel;
import br.com.zup.gerenciamentoEscolar.model.ProfessorModel;
import br.com.zup.gerenciamentoEscolar.service.LogEventosService;
import br.com.zup.gerenciamentoEscolar.service.ProfessorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/gerenciamentoEscolar", produces = {"application/json"})
@Tag(name = "Feature - Professores")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private LogEventosService logEventosService;

    @GetMapping("/professores")
    @Operation(summary = "Listar todos os professores", method = "GET")
    public ResponseEntity<List<ProfessorDTO>> listarTodosProfessores() {
        List<ProfessorModel> professoresEncontratos = professorService.listarTodosProfessores();

        List<ProfessorDTO> professoresDTO = new ArrayList<>();
        for (ProfessorModel professor : professoresEncontratos) {
            ProfessorDTO professorDTO = new ProfessorDTO(professor.getNome(), professor.getCurso().toString());
            professoresDTO.add(professorDTO);
        }

        logEventosService.gerarLogListarAll(TipoLogEvento.LISTOU_PROFESSORES);
        return ResponseEntity.ok(professoresDTO);
    }


    @GetMapping("/professores/{id}")
    @Operation(summary = "Listar um professor pelo ID", method = "GET")
    public ResponseEntity<?> listarProfessorPeloId(@PathVariable Long id) {
        Optional<ProfessorModel> professorEncontrado = professorService.buscarProfessorPeloId(id);

        if (professorEncontrado.isEmpty()) {
            ProfessorModel professorNull = new ProfessorModel();
            logEventosService.gerarLogBuscaDePeloId(professorNull, TipoLogEvento.PROFESSOR_NAO_ENCONTRADO);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Professor não encontrado, tente novamente!");
        }

        ProfessorModel professor = professorEncontrado.orElseThrow(() -> new NoSuchElementException("Professor não encontrado"));
        logEventosService.gerarLogBuscaDePeloId(professor, TipoLogEvento.LISTOU_PROFESSOR);
        return ResponseEntity.ok(professor);
    }

    @PostMapping("/professores")
    @Operation(summary = "Cadastrar um novo professor", method = "POST")
    public ResponseEntity<ProfessorModel> cadastrarProfessor(@RequestBody ProfessorModel professorModel) {
        ProfessorModel novoProfessor = professorService.criarProfessor(professorModel);
        logEventosService.gerarLogCadastroRealizado(novoProfessor, TipoLogEvento.PROFESSOR_CADASTRADO);
        return new ResponseEntity<>(novoProfessor, HttpStatus.CREATED);
    }

    @DeleteMapping("/professores/{id}")
    @Operation(summary = "Deletar um professor pelo ID", method = "DELETE")
    public void deletarProfessor(@PathVariable Long id) {
        Optional<ProfessorModel> professorEncontrado = professorService.buscarProfessorPeloId(id);
        ProfessorModel professor = professorEncontrado.orElseThrow(() -> new NoSuchElementException("Professor não encontrado"));
        logEventosService.gerarLogDeleteRealizado(professor, TipoLogEvento.PROFESSOR_DELETADO);
        professorService.deletarProfessor(id);
    }

}
