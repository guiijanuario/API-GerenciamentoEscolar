package br.com.zup.gerenciamentoEscolar.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_matriculas")
@Schema(name = "Matricula")
public class MatriculaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Número da Matricula", example = "YYYYMAT123")
    @Column(name = "numero_matricula")
    private String nroMatricula;

    @Schema(description = "Data da matricula", example = "YYYY-MM-DD")
    @Column(name = "data_matricula")
    private LocalDate dataMatricula;

    @Schema(description = "ID do Aluno", example = "1")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "aluno_id")
    private AlunoModel aluno;

    @Schema(description = "ID do Curso", example = "1")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "curso_id")
    private CursoModel curso;
}
