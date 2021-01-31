package by.bntu.fitr.poisit.lytkina.englishLearnBot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@PropertySource("classpath:application.properties")
@EnableTransactionManagement
public class DBConfiguration {

    @Value("${spring.datasource.password}")
    private String password;
}