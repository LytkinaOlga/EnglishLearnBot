package by.bntu.fitr.poisit.lytkina.englishLearnBot.repo;

import by.bntu.fitr.poisit.lytkina.englishLearnBot.bean.Word;
import org.springframework.data.repository.CrudRepository;

public interface WordRepoI extends CrudRepository<Word, Integer> {
}
