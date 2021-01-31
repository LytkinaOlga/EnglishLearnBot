package by.bntu.fitr.poisit.lytkina.englishLearnBot.bean;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Component
@Data
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    String russianWord;
    String englishWord;
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    User user;
}
