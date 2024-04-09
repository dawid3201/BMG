package BMG.BookManamgnet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class BookManamgnetApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookManamgnetApplication.class, args);
	}
}