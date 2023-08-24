package br.com.zup.gerenciamentoEscolar.model;

import br.com.zup.gerenciamentoEscolar.enums.CursosGraduacao;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_professores")
@Schema(name = "Professor")
public class ProfessorModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Nome do Professor", example = "Nome Completo")
    @Column(name = "nome")
    private String nome;

    @Schema(description = "Idade do Professor", example = "45")
    @Column(name = "idade")
    private int idade;

    @Schema(description = "Salário do Professor", example = "8000.0")
    @Column(name = "salario")
    private double salario;

    @Schema(description = "ID do Curso", example = "1")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "curso_id")
    private CursoModel curso;

    public ProfessorModel(long id, String professorGirafales, int idade, double salario, String s) {
    }
}
