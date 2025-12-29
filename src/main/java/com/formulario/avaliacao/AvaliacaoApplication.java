package com.formulario.avaliacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AvaliacaoApplication {

	public static void main(String[] args) {
		String port = System.getenv("PORT");
		if (port != null) {
			System.setProperty("server.port", port);
		}
		SpringApplication.run(AvaliacaoApplication.class, args);
	}

}
