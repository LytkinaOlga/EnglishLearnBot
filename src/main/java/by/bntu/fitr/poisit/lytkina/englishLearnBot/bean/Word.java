package by.bntu.fitr.poisit.lytkina.englishLearnBot.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Component
@Data
@Table(name="words")
@AllArgsConstructor
@NoArgsConstructor
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    String russianWord;
    String englishWord;
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    User user;

    public Word(String russianWord, String englishWord){
        this.englishWord = englishWord;
        this.russianWord = russianWord;
    }
}
