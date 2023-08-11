package br.com.zup.gerenciamentoEscolar;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "API - Gerenciamento Escolar", version = "1", description = "API desenvolvida para gerenciar uma escola"))
public class GerenciamentoEscolarApplication {

	public static void main(String[] args) {
		SpringApplication.run(GerenciamentoEscolarApplication.class, args);
	}

}
