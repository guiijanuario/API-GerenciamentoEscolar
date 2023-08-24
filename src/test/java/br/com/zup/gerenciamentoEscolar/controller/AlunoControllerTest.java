package br.com.zup.gerenciamentoEscolar.controller;

import br.com.zup.gerenciamentoEscolar.dto.AlunoDTO;
import br.com.zup.gerenciamentoEscolar.model.AlunoModel;
import br.com.zup.gerenciamentoEscolar.service.AlunoService;
import br.com.zup.gerenciamentoEscolar.service.LogEventosService;
import org.junit.jupiter.api.BeforeEach;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AlunoController.class)
public class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlunoService alunoService;

    @MockBean
    private LogEventosService logEventosService;

    private List<AlunoModel> alunosEncontrados;

    @BeforeEach
    void setUp() {
        alunosEncontrados = new ArrayList<>();
        alunosEncontrados.add(new AlunoModel(1L, "Guilherme Januário", 29, "gui@gmail.com"));
        alunosEncontrados.add(new AlunoModel(2L, "Mary Moura", 25, "mary@email.com"));
    }

    @Test
    void testListarTodosAlunos() throws Exception {
        when(alunoService.listarTodasContas()).thenReturn(alunosEncontrados);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/gerenciamentoEscolar/alunos"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(alunosEncontrados.size()));
    }


    @Test
    void testListarAlunoPorId() throws Exception {
        Long alunoId = 1L;
        AlunoModel aluno = new AlunoModel(alunoId, "Guilherme", 29, "gui@gmail.com");

        when(alunoService.buscarAlunoPeloId(alunoId)).thenReturn(Optional.of(aluno));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/gerenciamentoEscolar/alunos/{id}", alunoId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(aluno.getNome()));
    }

    @Test
    void cadastrarAluno() throws Exception {
        AlunoModel novoAluno = new AlunoModel(3L, "Guilherme", 29, "gui@example.com");

        when(alunoService.criarAluno(any())).thenReturn(novoAluno);

            mockMvc.perform(MockMvcRequestBuilders.post("/api/gerenciamentoEscolar/alunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Guilherme\",\"idade\":29,\"email\":\"gui@example.com\"}"))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(content().json("{\"nome\":\"Guilherme\",\"idade\":29,\"email\":\"gui@example.com\"}"));
    }

    @Test
    void deletarAluno() throws Exception {
        Long alunoId = 1L;
        AlunoModel alunoModel = new AlunoModel(1L,"Guilherme", 29, "gui@example.com");

        when(alunoService.buscarAlunoPeloId(alunoId)).thenReturn(Optional.of(alunoModel));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/gerenciamentoEscolar/alunos/1"))
                .andExpect(status().isOk())
                .andDo(print());

        verify(alunoService, times(1)).deletarAluno(alunoId);
    }

}

