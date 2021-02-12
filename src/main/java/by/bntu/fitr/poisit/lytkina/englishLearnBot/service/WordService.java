package by.bntu.fitr.poisit.lytkina.englishLearnBot.service;

import by.bntu.fitr.poisit.lytkina.englishLearnBot.bean.User;
import by.bntu.fitr.poisit.lytkina.englishLearnBot.bean.Word;
import by.bntu.fitr.poisit.lytkina.englishLearnBot.repo.WordRepoI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
@Service
public class WordService {
    @Autowired
    private WordRepoI wordRepository;



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
    String findAllWordsReturnString(User currentUser){
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
    ArrayList<Word> findAllWordsReturnList(User currentUser){
        ArrayList<Word> wordsList= new ArrayList<>();
        Iterable<Word> words = wordRepository.findAll();
        for (Word word : words) {
            if (word.getUser().getId().equals(currentUser.getId())){
                wordsList.add(word);
            }
        }

        return wordsList;
    }

    boolean checkIfExistEnoughWordsForTraining(ArrayList<Word> words){
        if (words.size() > 3){
            return true;
        }
        else return false;
    }

    Word createWordAndAdd(String str){
        Word word = new Word();
        String englishWord;
        String russianWord;
        int i;
        i = str.indexOf('-');
        englishWord = str.substring(0, i);

        russianWord = str.substring(i+1);

        word.setRussianWord(russianWord.replaceAll(" ", ""));
        word.setEnglishWord(englishWord.replaceAll(" ", ""));
        return word;
    }
}
