package youtube.java.puzzle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringBootMultipleDatasourceExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMultipleDatasourceExampleApplication.class, args);
	}

}
