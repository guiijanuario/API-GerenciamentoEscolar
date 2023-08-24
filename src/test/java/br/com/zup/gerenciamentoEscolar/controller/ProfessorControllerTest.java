package br.com.zup.gerenciamentoEscolar.controller;


import br.com.zup.gerenciamentoEscolar.enums.CursosGraduacao;
import br.com.zup.gerenciamentoEscolar.model.AlunoModel;
import br.com.zup.gerenciamentoEscolar.model.CursoModel;
import br.com.zup.gerenciamentoEscolar.model.ProfessorModel;
import br.com.zup.gerenciamentoEscolar.service.LogEventosService;
import br.com.zup.gerenciamentoEscolar.service.ProfessorService;
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

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProfessorController.class)
public class ProfessorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfessorService professorService;

    @MockBean
    private LogEventosService logEventosService;

    @Test
    void testListarTodosProfessores() throws Exception {
        List<ProfessorModel> professores = new ArrayList<>();
        CursoModel curso = new CursoModel(1L,CursosGraduacao.CIENCIA_DA_COMPUTACAO, 1200);
        CursoModel curso2 = new CursoModel(1L,CursosGraduacao.ENGENHARIA_DE_SOFTWARE, 1600);
        professores.add(new ProfessorModel(1L, "Professor Girafales",35, 2500.00, curso));
        professores.add(new ProfessorModel(1L, "Professor Barriga",40, 2800.00, curso2));

        when(professorService.listarTodosProfessores()).thenReturn(professores);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/gerenciamentoEscolar/professores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

//    @Test
//    void testListarProfessorPeloId() throws Exception {
//             Long professorId = 1L;
//        ProfessorModel professor = new ProfessorModel(1L, "Professor Girafales",35, 2500.00, String.valueOf(CursosGraduacao.CIENCIA_DA_COMPUTACAO));
//
//        when(professorService.buscarProfessorPeloId(professorId)).thenReturn(Optional.of(professor));
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/gerenciamentoEscolar/professor/{id}", professorId))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(professor.getNome()));
//    }

    @Test
    void testListarProfessorPeloIdNaoEncontrado() throws Exception {
        Long professorId = 1L;

        when(professorService.buscarProfessorPeloId(professorId)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/gerenciamentoEscolar/professores/{id}", professorId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Professor nÃ£o encontrado, tente novamente!"));
    }

    @Test
    void testCadastrarProfessor() throws Exception {
        CursoModel curso = new CursoModel(1L,CursosGraduacao.CIENCIA_DA_COMPUTACAO, 1200);
        ProfessorModel novoProfessor = new ProfessorModel(1L, "Novo Professor Girafales",35, 2500.00, curso);

        when(professorService.criarProfessor(any())).thenReturn(novoProfessor);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/gerenciamentoEscolar/professores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Novo Professor Girafales\",\"idade\":35,\"salario\":2500.00,\"curso\":{\"id\":1,\"nome\":\"CIENCIA_DA_COMPUTACAO\",\"cargaHoraria\":1200}}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Novo Professor Girafales"))
                .andExpect(jsonPath("$.curso.nome").value("CIENCIA_DA_COMPUTACAO"));
    }

    @Test
    void testDeletarProfessor() throws Exception {
        Long professorId = 1L;
        CursoModel curso = new CursoModel(1L,CursosGraduacao.CIENCIA_DA_COMPUTACAO, 1200);
        ProfessorModel professor = new ProfessorModel(1L, "Novo Professor Girafales",35, 2500.00, curso);

        when(professorService.buscarProfessorPeloId(professorId)).thenReturn(Optional.of(professor));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/gerenciamentoEscolar/professores/{id}", professorId))
                .andExpect(status().isOk());

        verify(professorService, times(1)).deletarProfessor(professorId);
    }
}
