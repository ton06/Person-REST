package br.com.wellington.o.a.calculadorarest;

import br.com.wellington.o.a.calculadorarest.config.FileStorageConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
@EnableConfigurationProperties({
		FileStorageConfig.class
})
public class CalculadoraRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalculadoraRestApplication.class, args);

//		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(16);
//		String result = bCryptPasswordEncoder.encode("admin123");
//		System.out.println("Hash: " + result);
	}

}
