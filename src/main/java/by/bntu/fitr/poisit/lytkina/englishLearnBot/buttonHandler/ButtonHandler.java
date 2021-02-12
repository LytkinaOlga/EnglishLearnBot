package by.bntu.fitr.poisit.lytkina.englishLearnBot.buttonHandler;

import by.bntu.fitr.poisit.lytkina.englishLearnBot.bean.Word;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
@Component
public class ButtonHandler {
    public ReplyKeyboardMarkup getMainMenuKeyboard() {

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();


        row1.add(new KeyboardButton("Добавить слово-перевод"));
        row2.add(new KeyboardButton("Найти слово (вводить русское)"));
        row2.add(new KeyboardButton("Найти слово (вводить английское)"));
        row3.add(new KeyboardButton("Тренировка (русский - английский)"));
        row3.add(new KeyboardButton("Тренировка (английский - русский)"));
        row1.add(new KeyboardButton("Мой словарь"));


        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }
    public SendMessage getButtonMainMenu(long chatId, String text) {
        return new SendMessage(chatId, text).setReplyMarkup(getMainMenuKeyboard());
    }

}
