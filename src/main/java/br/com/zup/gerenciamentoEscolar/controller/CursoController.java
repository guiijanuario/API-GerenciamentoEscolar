package br.com.zup.gerenciamentoEscolar.controller;

import br.com.zup.gerenciamentoEscolar.dto.CursoDTO;
import br.com.zup.gerenciamentoEscolar.enums.TipoLogEvento;
import br.com.zup.gerenciamentoEscolar.model.CursoModel;
import br.com.zup.gerenciamentoEscolar.service.CursoService;
import br.com.zup.gerenciamentoEscolar.service.LogEventosService;
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
@Tag(name = "Feature - Cursos")
public class CursoController {

    @Autowired
    CursoService cursoService;

    @Autowired
    LogEventosService logEventosService;

    @GetMapping("/cursos")
    @Operation(summary = " : Lista todos os cursos", method = "GET")
    public ResponseEntity<List<CursoDTO>> listarTodosCursos() {
        List<CursoModel> cursosEncontrados = cursoService.listarTodosCursos();

        List<CursoDTO>  cursosDTO = new ArrayList<>();
        for(CursoModel curso : cursosEncontrados){
            CursoDTO cursoDTO = new CursoDTO(curso.getNome().toString(), curso.getCargaHoraria());
            cursosDTO.add(cursoDTO);
        }
        logEventosService.gerarLogListarAll(TipoLogEvento.LISTOU_CURSOS);
        return ResponseEntity.ok(cursosDTO);
    }

    @GetMapping("/cursos/{id}")
    @Operation(summary = " : Lista um curso pelo ID", method = "GET")
    public ResponseEntity<?> listarCursoId(@PathVariable Long id) {
        Optional<CursoModel> cursoEncontrado = cursoService.buscarCursoPeloId(id);

        if (cursoEncontrado.isEmpty()) {
            CursoModel cursoNull = new CursoModel();
            logEventosService.gerarLogBuscaDePeloId(cursoNull, TipoLogEvento.CURSO_NAO_ENCONTRADO);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso não encontrado, tente novamente!");
        }

        CursoModel curso = cursoEncontrado.orElseThrow(() -> new NoSuchElementException("Curso não encontrado"));
        logEventosService.gerarLogBuscaDePeloId(curso, TipoLogEvento.LISTOU_CURSO);
        return ResponseEntity.ok(cursoEncontrado.get());
    }

    @PostMapping("/cursos")
    @Operation(summary = " : Cadastra um novo curso", method = "POST")
    public ResponseEntity<CursoModel> cadastrarCurso(@RequestBody CursoModel cursoModel) {
        CursoModel novoCurso = cursoService.criarCurso(cursoModel);
        logEventosService.gerarLogCadastroRealizado(novoCurso, TipoLogEvento.CURSO_CADASTRADO);
        return new ResponseEntity<>(novoCurso, HttpStatus.CREATED);
    }


    @DeleteMapping("/cursos/{id}")
    @Operation(summary = " : Deleta um curso pelo ID", method = "DELETE")
    public void deletarCurso(@PathVariable Long id) {
        Optional<CursoModel> cursoEncontrado = cursoService.buscarCursoPeloId(id);
        CursoModel curso = cursoEncontrado.orElseThrow(() -> new NoSuchElementException("Curso não encontrado"));
        logEventosService.gerarLogDeleteRealizado(curso, TipoLogEvento.CURSO_DELETADO);
        cursoService.deletarCurso(id);
    }


}
