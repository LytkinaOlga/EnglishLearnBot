package by.bntu.fitr.poisit.lytkina.englishLearnBot.bean;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Component
@Table(name="users")
public class User {
    @Id
    Integer id;
    String name;
    @OneToMany(mappedBy = "user")
    List<Word> words;
}
