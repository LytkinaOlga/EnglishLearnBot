package by.bntu.fitr.poisit.lytkina.englishLearnBot.service;

import by.bntu.fitr.poisit.lytkina.englishLearnBot.bean.Word;
import by.bntu.fitr.poisit.lytkina.englishLearnBot.repo.WordRepoI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class WordService {
    @Autowired
    private WordRepoI wordRepository;
    private Optional<Word> person;

    void saveWord(Word word){
        wordRepository.save(word);
    }
    String findByRussianWord(String russianWord){
        Iterable<Word> words = wordRepository.findAll();
        for (Word word : words) {
            if (word.getRussianWord().equals(russianWord)){
                return word.getEnglishWord();
            }
        }
        return "Не найдено!";
    }
    String findByEnglishWord(){
        return "";
    }
}
