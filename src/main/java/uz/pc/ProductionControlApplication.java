package uz.pc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ProductionControlApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ProductionControlApplication.class, args);
	}

	/*
	 * Method helps to build application in correct way with compiling its classes
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ProductionControlApplication.class);
	}

}
