package by.bntu.fitr.poisit.lytkina.englishLearnBot.service;

import by.bntu.fitr.poisit.lytkina.englishLearnBot.bean.User;
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

    String findByRussianWord(String russianWord, User currentUser){
        Iterable<Word> words = wordRepository.findAll();
        for (Word word : words) {
            if (word.getRussianWord().equals(russianWord) && word.getUser().getId().equals(currentUser.getId())){
                return word.getEnglishWord();
            }
        }
        return "Не найдено!";
    }
    String findByEnglishWord(String englishWord, User currentUser){
        Iterable<Word> words = wordRepository.findAll();
        for (Word word : words) {
            if (word.getEnglishWord().equals(englishWord) && word.getUser().getId().equals(currentUser.getId())){
                return word.getRussianWord();
            }
        }
        return "Не найдено!";
    }
    String findAllWords(User currentUser){
        String result = "";
        Iterable<Word> words = wordRepository.findAll();
        for (Word word : words) {
            if (word.getUser().getId().equals(currentUser.getId())){
                result+=word.getRussianWord() + " - " + word.getEnglishWord() + "\n";
            }
        }
        if (result.equals("")){
            result+="Ваш словарь пуст!";
        }
        return result;
    }
}
