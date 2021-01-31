package by.bntu.fitr.poisit.lytkina.englishLearnBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class EnglishLearnBotApplication {

	public static void main(String[] args) {
		ApiContextInitializer.init();
		SpringApplication.run(EnglishLearnBotApplication.class, args);
	}

}
