package by.bntu.fitr.poisit.lytkina.englishLearnBot.repo;

import by.bntu.fitr.poisit.lytkina.englishLearnBot.bean.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepoI extends CrudRepository<User, Integer> {
}
