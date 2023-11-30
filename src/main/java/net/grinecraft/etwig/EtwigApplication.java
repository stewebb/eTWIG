package net.grinecraft.etwig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class EtwigApplication {

	public static void main(String[] args) {
		SpringApplication.run(EtwigApplication.class, args);
	}
	

    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(EtwigApplication.class);
    }

}
