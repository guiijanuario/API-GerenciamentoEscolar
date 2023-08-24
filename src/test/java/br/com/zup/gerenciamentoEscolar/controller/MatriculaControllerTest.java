package br.com.zup.gerenciamentoEscolar.controller;

import br.com.zup.gerenciamentoEscolar.model.MatriculaModel;
import br.com.zup.gerenciamentoEscolar.service.AlunoService;
import br.com.zup.gerenciamentoEscolar.service.CursoService;
import br.com.zup.gerenciamentoEscolar.service.LogEventosService;
import br.com.zup.gerenciamentoEscolar.service.MatriculaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MatriculaController.class)
public class MatriculaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MatriculaService matriculaService;

    @MockBean
    private LogEventosService logEventosService;

    @MockBean
    private AlunoService alunoService;

    @MockBean
    private CursoService cursoService;

    @Test
    public void testListarTodasMatriculas() throws Exception {
        when(matriculaService.listarTodasMatriculas()).thenReturn(new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/gerenciamentoEscolar/matriculas"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());

        verify(matriculaService, times(1)).listarTodasMatriculas();
    }

    @Test
    public void testListarMatriculaPorId() throws Exception {
        Long matriculaId = 1L;
        MatriculaModel matricula = new MatriculaModel();

        when(matriculaService.buscarMatriculaPeloId(matriculaId)).thenReturn(Optional.of(matricula));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/gerenciamentoEscolar/matriculas/{id}", matriculaId))
                .andDo(print())
                .andExpect(status().isOk());

        verify(matriculaService, times(1)).buscarMatriculaPeloId(matriculaId);
    }

    @Test
    public void testCadastrarMatricula() throws Exception {
        MatriculaModel novaMatricula = new MatriculaModel();

        when(matriculaService.criarMatricula(any())).thenReturn(novaMatricula);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/gerenciamentoEscolar/matriculas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"nroMatricula\": \"2023MAT123\",\n" +
                                "  \"dataMatricula\": \"2023-08-10\",\n" +
                                "  \"aluno\": {\n" +
                                "    \"id\": 1\n" +
                                "  },\n" +
                                "  \"curso\": {\n" +
                                "    \"id\": 1\n" +
                                "  }\n" +
                                "}"))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(matriculaService, times(1)).criarMatricula(any());
    }
}
