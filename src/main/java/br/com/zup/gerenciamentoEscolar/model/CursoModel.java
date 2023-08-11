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
@Table(name = "tb_cursos")
@Schema(name = "Curso")
public class CursoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Nome do Curso", example = "CIENCIA_DA_COMPUTACAO")
    @Enumerated(EnumType.STRING)
    @Column(name = "nome")
    private CursosGraduacao nome;

    @Schema(description = "Carga Horária", example = "1600")
    @Column(name = "carga_horaria")
    private int cargaHoraria;
}
