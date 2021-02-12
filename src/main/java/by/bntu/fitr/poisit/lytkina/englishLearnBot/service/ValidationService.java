package by.bntu.fitr.poisit.lytkina.englishLearnBot.service;

import by.bntu.fitr.poisit.lytkina.englishLearnBot.bean.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ValidationService {
    @Autowired
    Word word;


    public boolean checkIfTextInEnglish(String text){
        boolean flag = false;
        char[] englishAlphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', ' '};
        char[] textArray = text.toLowerCase().toCharArray();
        for (char letter : textArray){
            flag = false;
            for (char alpha : englishAlphabet){
                if (letter==alpha){
                    flag = true;
                    break;
                }else flag = false;
            }
            if (!flag){
                return false;
            }
        }
        return flag;
    }

    public boolean checkIfTextInRussian(String text){
        boolean flag = false;
        char[] englishAlphabet = {'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ь', 'ы', 'ъ', 'э', 'ю', 'я', ' '};
        char[] textArray = text.toLowerCase().toCharArray();
        for (char letter : textArray){
            flag = false;
            for (char alpha : englishAlphabet){
                if (letter==alpha){
                    flag = true;
                    break;
                }else flag = false;
            }
            if (!flag){
                return false;
            }
        }
        return flag;
    }

    public boolean checkIfTextInRussianWithUnicode(String text){
        boolean result = true;
        for (char a : text.toCharArray()) {
            if (Character.UnicodeBlock.of(a) != Character.UnicodeBlock.CYRILLIC && a !=' ') {
                return false;
            }
        }
        return result;
    }



    public Word returnWord(String str){
        String someWord, otherPartOfWord;
        int i;
        i = str.indexOf(' ');
        someWord = str.substring(0, i);
        otherPartOfWord = str.substring(i+1);
        if (checkIfTextInRussian(someWord.toLowerCase())){
            word.setRussianWord(someWord.replaceAll(" ", ""));
        }else if (checkIfTextInEnglish(someWord.toLowerCase())){
            word.setEnglishWord(someWord.replaceAll(" ", ""));
        }
        if (checkIfTextInRussian(otherPartOfWord.toLowerCase())){
            word.setRussianWord(otherPartOfWord.replaceAll(" ", ""));
        }else if (checkIfTextInEnglish(otherPartOfWord.toLowerCase())){
            word.setEnglishWord(otherPartOfWord.replaceAll(" ", ""));
        }
        return word;
    }

}
