package org.fatec.esportiva;

import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class EsportivaApplication {

	public static void main(String[] args) {
		if (Files.exists(Paths.get(".env"))) {
			Dotenv dotenv = Dotenv.configure().load();
			dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
		}

		SpringApplication.run(EsportivaApplication.class, args);
	}

}
