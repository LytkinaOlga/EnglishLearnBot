package by.bntu.fitr.poisit.lytkina.englishLearnBot.bean;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@Component
public class User {
    @Id
    Integer id;
    String name;
}
