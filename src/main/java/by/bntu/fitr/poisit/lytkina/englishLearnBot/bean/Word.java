package by.bntu.fitr.poisit.lytkina.englishLearnBot.bean;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Component
@Data
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    String russianWord;
    String englishWord;
}
