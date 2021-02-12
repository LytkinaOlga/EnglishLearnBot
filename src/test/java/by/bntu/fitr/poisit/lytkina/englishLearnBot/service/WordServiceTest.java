package by.bntu.fitr.poisit.lytkina.englishLearnBot.service;

import by.bntu.fitr.poisit.lytkina.englishLearnBot.bean.Word;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WordServiceTest {
    WordService wordService;

    @Before
    public void init(){
        wordService = new WordService();
    }

    @Test
    public void createWordAndAdd() {
        String str = "car - машина";
        Word expectedWord = new Word( "машина", "car");
        Word actualWord = wordService.createWordAndAdd(str);
        assertEquals(expectedWord, actualWord);
    }
}