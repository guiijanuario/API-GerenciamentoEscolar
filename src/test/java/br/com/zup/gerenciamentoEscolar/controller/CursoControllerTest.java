package br.com.zup.gerenciamentoEscolar.controller;

import br.com.zup.gerenciamentoEscolar.enums.CursosGraduacao;
import br.com.zup.gerenciamentoEscolar.enums.TipoLogEvento;
import br.com.zup.gerenciamentoEscolar.model.CursoModel;
import br.com.zup.gerenciamentoEscolar.service.CursoService;
import br.com.zup.gerenciamentoEscolar.service.LogEventosService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CursoController.class)
public class CursoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CursoService cursoService;

    @MockBean
    private LogEventosService logEventosService;

    @Test
    public void testListarTodosCursos() throws Exception {
        // Crie uma lista de cursos simulada para retornar no mock
        List<CursoModel> cursosSimulados = new ArrayList<>();
        cursosSimulados.add(new CursoModel(1L,CursosGraduacao.CIENCIA_DA_COMPUTACAO, 1200));
        cursosSimulados.add(new CursoModel(2L,CursosGraduacao.SISTEMAS_DE_INFORMACAO, 1600));

        // Configure o comportamento do mock do cursoService
        when(cursoService.listarTodosCursos()).thenReturn(cursosSimulados);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/gerenciamentoEscolar/cursos"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(cursosSimulados.size()));

    }

    @Test
    void testListarCursoPorId() throws Exception {
        Long cursoId = 1L;
        CursoModel curso = new CursoModel(cursoId, CursosGraduacao.CIENCIA_DA_COMPUTACAO,29);

        when(cursoService.buscarCursoPeloId(cursoId)).thenReturn(Optional.of(curso));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/gerenciamentoEscolar/cursos/{id}", cursoId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(curso.getNome().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cargaHoraria").value(curso.getCargaHoraria()));
    }

    @Test
    public void testCadastrarCurso() throws Exception {
        CursoModel novoCurso = new CursoModel(1L, CursosGraduacao.CIENCIA_DA_COMPUTACAO, 29);

        when(cursoService.criarCurso(any())).thenReturn(novoCurso);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/gerenciamentoEscolar/cursos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"CIENCIA_DA_COMPUTACAO\",\"cargaHoraria\":29}"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("CIENCIA_DA_COMPUTACAO"))
                .andExpect(jsonPath("$.cargaHoraria").value(29));

        verify(logEventosService, times(1)).gerarLogCadastroRealizado(any(), eq(TipoLogEvento.CURSO_CADASTRADO));
    }

    @Test
    public void testDeletarCurso() throws Exception {
        Long cursoId = 1L;
        CursoModel curso = new CursoModel(cursoId, CursosGraduacao.CIENCIA_DA_COMPUTACAO, 29);

        when(cursoService.buscarCursoPeloId(cursoId)).thenReturn(Optional.of(curso));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/gerenciamentoEscolar/cursos/{id}", cursoId))
                .andDo(print())
                .andExpect(status().isOk());

        verify(logEventosService, times(1)).gerarLogDeleteRealizado(any(), eq(TipoLogEvento.CURSO_DELETADO));
        verify(cursoService, times(1)).deletarCurso(cursoId);
    }
}
