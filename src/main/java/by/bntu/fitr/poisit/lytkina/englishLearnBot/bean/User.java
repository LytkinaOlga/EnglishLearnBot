package by.bntu.fitr.poisit.lytkina.englishLearnBot.bean;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class User {
    @Id

    Long id;
    String name;
}
