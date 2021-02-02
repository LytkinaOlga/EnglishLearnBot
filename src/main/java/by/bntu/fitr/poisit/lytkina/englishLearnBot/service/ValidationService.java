package by.bntu.fitr.poisit.lytkina.englishLearnBot.service;

import org.springframework.stereotype.Service;


@Service
public class ValidationService {

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

}
