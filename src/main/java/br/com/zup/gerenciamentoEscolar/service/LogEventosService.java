package br.com.zup.gerenciamentoEscolar.service;

import br.com.zup.gerenciamentoEscolar.enums.TipoLogEvento;
import br.com.zup.gerenciamentoEscolar.model.*;
import br.com.zup.gerenciamentoEscolar.repository.LogEventosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@Service
public class LogEventosService {

    private static final Logger logger = LoggerFactory.getLogger(LogEventosModel.class);

    @Autowired
    LogEventosRepository logEventosRepository;

    public void gerarLogListarAll(TipoLogEvento tipoLogEvento) {
        LogEventosModel listagemLog = new LogEventosModel();

        switch (tipoLogEvento) {
            case LISTOU_ALUNOS -> {
                listagemLog.setEvento("[LOG] - Listou todos os alunos");
                listagemLog.setTipoLogEvento(tipoLogEvento);
                logger.info(listagemLog.getEvento());
                logEventosRepository.save(listagemLog);
            }
            case LISTOU_CURSOS -> {
                listagemLog.setEvento("[LOG] - Listou todos os Cursos");
                listagemLog.setTipoLogEvento(tipoLogEvento);
                logger.info(listagemLog.getEvento());
                logEventosRepository.save(listagemLog);
            }
            case LISTOU_MATRICULAS -> {
                listagemLog.setEvento("[LOG] - Listou todas as matriculas");
                listagemLog.setTipoLogEvento(tipoLogEvento);
                logger.info(listagemLog.getEvento());
                logEventosRepository.save(listagemLog);
            }
            case LISTOU_PROFESSORES -> {
                listagemLog.setEvento("[LOG] - Listou todos os professores");
                listagemLog.setTipoLogEvento(tipoLogEvento);
                logger.info(listagemLog.getEvento());
                logEventosRepository.save(listagemLog);
            }
            default -> throw new IllegalArgumentException("Tipo de LogEventos não suportado: " + tipoLogEvento);
        }
    }

    public <T> void gerarLogBuscaDePeloId(T entidade, TipoLogEvento tipoLogEvento) {
        LogEventosModel listagemBuscaLog = new LogEventosModel();

        if (entidade instanceof AlunoModel aluno) {
            listagemBuscaLog.setEvento(String.format("[LOG] - Aluno com ID %d, nome '%s', idade %d e email '%s' foi encontrado.",
                    aluno.getId(),
                    aluno.getNome(),
                    aluno.getIdade(),
                    aluno.getEmail()));
        } else if (entidade instanceof CursoModel curso) {
            listagemBuscaLog.setEvento(String.format("[LOG] - Curso com ID %d, nome '%s', carga horária %d foi encontrado.",
                    curso.getId(),
                    curso.getNome(),
                    curso.getCargaHoraria()));
        } else if (entidade instanceof MatriculaModel matricula) {
            listagemBuscaLog.setEvento(String.format("[LOG] - Matrícula com ID %d, data de matrícula %s, aluno %s, curso %s foi encontrada.",
                    matricula.getId(),
                    matricula.getDataMatricula(),
                    matricula.getAluno().getNome(),
                    matricula.getCurso().getNome()));
        } else if (entidade instanceof ProfessorModel professor) {
            listagemBuscaLog.setEvento(String.format("[LOG] - Professor com ID %d, nome '%s', idade %d, salário %.2f, curso %s foi encontrado.",
                    professor.getId(),
                    professor.getNome(),
                    professor.getIdade(),
                    professor.getSalario(),
                    professor.getCurso().getNome()));
        }else {
            throw new IllegalArgumentException("Tipo de entidade não suportado: " + entidade.getClass());
        }

        listagemBuscaLog.setTipoLogEvento(tipoLogEvento);
        logger.info(listagemBuscaLog.getEvento());
        logEventosRepository.save(listagemBuscaLog);
    }

    public <T> void gerarLogCadastroRealizado(T entidade, TipoLogEvento tipoLogEvento) {
        LogEventosModel listagemBuscaLog = new LogEventosModel();

        if (entidade instanceof AlunoModel aluno) {
            listagemBuscaLog.setEvento(String.format("[LOG] - Aluno com ID %d, nome '%s', idade %d e email '%s' foi cadastrado.",
                    aluno.getId(),
                    aluno.getNome(),
                    aluno.getIdade(),
                    aluno.getEmail()));
        } else if (entidade instanceof CursoModel curso) {
            listagemBuscaLog.setEvento(String.format("[LOG] - Curso com ID %d, nome '%s', carga horária %d foi cadastrado.",
                    curso.getId(),
                    curso.getNome(),
                    curso.getCargaHoraria()));
        } else if (entidade instanceof MatriculaModel matricula) {
            listagemBuscaLog.setEvento(String.format("[LOG] - Matrícula com ID %d, data de matrícula %s, aluno %s, curso %s foi cadastrado.",
                    matricula.getId(),
                    matricula.getDataMatricula(),
                    matricula.getAluno().getNome(),
                    matricula.getCurso().getNome()));
        } else if (entidade instanceof ProfessorModel professor) {
            listagemBuscaLog.setEvento(String.format("[LOG] - Professor com ID %d, nome '%s', idade %d, salário %.2f, curso %s foi cadastrado.",
                    professor.getId(),
                    professor.getNome(),
                    professor.getIdade(),
                    professor.getSalario(),
                    professor.getCurso().getNome()));
        } else {
            throw new IllegalArgumentException("Tipo de entidade não suportado: " + entidade.getClass());
        }

        listagemBuscaLog.setTipoLogEvento(tipoLogEvento);
        logger.info(listagemBuscaLog.getEvento());
        logEventosRepository.save(listagemBuscaLog);
    }

    public <T> void gerarLogDeleteRealizado(T entidade, TipoLogEvento tipoLogEvento) {
        LogEventosModel listagemBuscaLog = new LogEventosModel();

        if (entidade instanceof AlunoModel aluno) {
            listagemBuscaLog.setEvento(String.format("[LOG] - Aluno com ID %d, nome '%s', idade %d e email '%s' foi deletado.",
                    aluno.getId(),
                    aluno.getNome(),
                    aluno.getIdade(),
                    aluno.getEmail()));
        } else if (entidade instanceof CursoModel curso) {
            listagemBuscaLog.setEvento(String.format("[LOG] - Curso com ID %d, nome '%s', carga horária %d foi deletado.",
                    curso.getId(),
                    curso.getNome(),
                    curso.getCargaHoraria()));
        } else if (entidade instanceof MatriculaModel matricula) {
            listagemBuscaLog.setEvento(String.format("[LOG] - Matrícula com ID %d, data de matrícula %s, aluno %s, curso %s foi deletado.",
                    matricula.getId(),
                    matricula.getDataMatricula(),
                    matricula.getAluno().getNome(),
                    matricula.getCurso().getNome()));
        } else if (entidade instanceof ProfessorModel professor) {
            listagemBuscaLog.setEvento(String.format("[LOG] - Professor com ID %d, nome '%s', idade %d, salário %.2f, curso %s foi deletado.",
                    professor.getId(),
                    professor.getNome(),
                    professor.getIdade(),
                    professor.getSalario(),
                    professor.getCurso().getNome()));
        } else {
            throw new IllegalArgumentException("Tipo de entidade não suportado: " + entidade.getClass());
        }

        listagemBuscaLog.setTipoLogEvento(tipoLogEvento);
        logger.info(listagemBuscaLog.getEvento());
        logEventosRepository.save(listagemBuscaLog);
    }

    public void gerarLogAtualizacaoRealizada(MatriculaModel matricula, TipoLogEvento tipoLogEvento) {
        LogEventosModel logAtualizacao = new LogEventosModel();

        logAtualizacao.setEvento(String.format("Matrícula com ID %d, do aluno %s, teve o curso atualizado para %s.",
                matricula.getId(),
                matricula.getAluno().getNome(),
                matricula.getCurso().getNome()));
        logAtualizacao.setTipoLogEvento(tipoLogEvento);

        logger.info(logAtualizacao.getEvento());
        logEventosRepository.save(logAtualizacao);
    }



}
