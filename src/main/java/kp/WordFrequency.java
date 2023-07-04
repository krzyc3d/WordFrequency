package kp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class WordFrequency {

	public static void main(String[] args) {
		SpringApplication.run(WordFrequency.class, args);
	}

}
