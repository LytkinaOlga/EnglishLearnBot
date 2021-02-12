package by.bntu.fitr.poisit.lytkina.englishLearnBot.service;

import by.bntu.fitr.poisit.lytkina.englishLearnBot.bean.User;
import by.bntu.fitr.poisit.lytkina.englishLearnBot.bean.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class TrainingService {

    @Autowired
    WordService wordService;
    @Autowired
    MessageService messageService;


    public void trainRussianEnglish(User user, Update update){
        ArrayList<Word> words = wordService.findAllWordsReturnList(user);


            Word word = getRandomWord(words);
            if (update.hasMessage()){
                messageService.sendMessage(getButtonInputDataRussianEnglish(update.getMessage().getChatId(), words, word));
            }else if (update.hasCallbackQuery()){
                messageService.sendMessage(getButtonInputDataRussianEnglish(update.getCallbackQuery().getMessage().getChatId(), words, word));
            }

    }
    public void trainEnglishRussian(User user, Update update){
        ArrayList<Word> words = wordService.findAllWordsReturnList(user);


        Word word = getRandomWord(words);
        if (update.hasMessage()){
            messageService.sendMessage(getButtonInputDataEnglishRussian(update.getMessage().getChatId(), words, word));
        }else if (update.hasCallbackQuery()){
            messageService.sendMessage(getButtonInputDataEnglishRussian(update.getCallbackQuery().getMessage().getChatId(), words, word));
        }

    }

    public Word getRandomWord(ArrayList<Word> words){
        Random random = new Random();
        int randomIndex = random.nextInt(words.size());
        return words.get(randomIndex);
    }
    public InlineKeyboardMarkup getInlineKeyboardMarkupRussianEnglish(ArrayList<Word> words, Word rightWord) {
        ArrayList<Word> options = getOptions(words, rightWord);
        Collections.shuffle(options);

        final InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();


        for (int i = 0; i< 3; i++){
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(options.get(i).getEnglishWord());
            if (options.get(i).getEnglishWord().equals(rightWord.getEnglishWord())){
                button.setCallbackData("RightAnswer");
            }else button.setCallbackData("WrongAnswer");
            List<InlineKeyboardButton> row = new ArrayList<>();
            row.add(button);
            keyboard.add(row);
        }

        inlineKeyboardMarkup.setKeyboard(keyboard);


        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getInlineKeyboardMarkupEnglishRussian(ArrayList<Word> words, Word rightWord) {
        ArrayList<Word> options = getOptions(words, rightWord);
        Collections.shuffle(options);

        final InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();


        for (int i = 0; i< 3; i++){
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(options.get(i).getRussianWord());
            if (options.get(i).getRussianWord().equals(rightWord.getRussianWord())){
                button.setCallbackData("RightAnswer");
            }else button.setCallbackData("WrongAnswer");
            List<InlineKeyboardButton> row = new ArrayList<>();
            row.add(button);
            keyboard.add(row);
        }

        inlineKeyboardMarkup.setKeyboard(keyboard);


        return inlineKeyboardMarkup;
    }
    public SendMessage getButtonInputDataRussianEnglish(long chatId, ArrayList<Word> words, Word rightWord) {
        return new SendMessage(chatId, rightWord.getRussianWord()).setReplyMarkup(getInlineKeyboardMarkupRussianEnglish(words, rightWord));
    }
    public SendMessage getButtonInputDataEnglishRussian(long chatId, ArrayList<Word> words, Word rightWord) {
        return new SendMessage(chatId, rightWord.getEnglishWord()).setReplyMarkup(getInlineKeyboardMarkupEnglishRussian(words, rightWord));
    }

    public ArrayList<Word> getOptions(ArrayList<Word> words, Word rightWord){
        words.remove(rightWord);
        ArrayList<Word> options = new ArrayList<>();
        options.add(rightWord);

        Word secondWord = getRandomWord(words);
        options.add(secondWord);
        words.remove(secondWord);

        Word thirdWord = getRandomWord(words);
        options.add(thirdWord);
        return  options;
    }
}
