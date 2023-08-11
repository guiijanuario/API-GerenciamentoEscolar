package br.com.zup.gerenciamentoEscolar.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_alunos")
@Schema(name = "Aluno")
public class AlunoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Nome do Aluno", example = "Nome Completo")
    @Column(name = "nome")
    private String nome;

    @Schema(description = "Idade", example = "25")
    @Column(name = "idade")
    private int idade;

    @Schema(description = "Email do Aluno", example = "aluno@email.com.br")
    @Column(name = "email")
    private String email;
}
