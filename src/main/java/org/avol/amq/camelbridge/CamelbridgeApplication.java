package org.avol.amq.camelbridge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class CamelbridgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamelbridgeApplication.class, args);
	}
}
