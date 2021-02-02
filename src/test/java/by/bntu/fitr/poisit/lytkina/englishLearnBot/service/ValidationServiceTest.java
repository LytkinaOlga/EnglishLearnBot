package by.bntu.fitr.poisit.lytkina.englishLearnBot.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class ValidationServiceTest {
    ValidationService validationService;
    @Before
    public void  init(){
        validationService = new ValidationService();
    }

    @Test
    public void checkIfTextInEnglishReturnTrue() {
        boolean actual = validationService.checkIfTextInEnglish("hello world");
        assertEquals(true, actual);
    }
    @Test
    public void checkIfTextInEnglishReturnFalse() {
        boolean actual = validationService.checkIfTextInEnglish("helдo world");
        assertEquals(false, actual);
    }


    @Test
    public void checkIfTextInRussianWithUnicodeReturnTrue () {
        boolean actual = validationService.checkIfTextInRussianWithUnicode("приет всем");
        assertEquals(true, actual);
    }
    @Test
    public void checkIfTextInRussianWithUnicodeReturnFalse() {
        boolean actual = validationService.checkIfTextInRussianWithUnicode("приеf всем");
        assertEquals(false, actual);
    }
}