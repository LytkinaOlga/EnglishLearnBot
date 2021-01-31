package by.bntu.fitr.poisit.lytkina.englishLearnBot.service;

import by.bntu.fitr.poisit.lytkina.englishLearnBot.bean.Word;
import by.bntu.fitr.poisit.lytkina.englishLearnBot.buttonHandler.ButtonHandler;
import by.bntu.fitr.poisit.lytkina.englishLearnBot.enums.BotCommands;
import by.bntu.fitr.poisit.lytkina.englishLearnBot.processor.HelpProcessor;
import by.bntu.fitr.poisit.lytkina.englishLearnBot.processor.NoneProcessor;
import by.bntu.fitr.poisit.lytkina.englishLearnBot.processor.SettingsProcessor;
import by.bntu.fitr.poisit.lytkina.englishLearnBot.processor.StartProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class RequestDispatcher {

    @Autowired
    MessageService messageService;
    @Autowired
    StartProcessor startProcessor;
    @Autowired
    HelpProcessor helpProcessor;
    @Autowired
    SettingsProcessor settingsProcessor;
    @Autowired
    NoneProcessor noneProcessor;
    @Autowired
    ButtonHandler buttonHandler;
    @Autowired
    Word word;
    @Autowired
    WordService wordService;

    private String msgText;

    private static String state = "";
    private static String russianWord = "";
    private static String englishWord = "";

    public void dispatch(Update update) {
        switch (getCommand(update)) {
            case HELP:
                messageService.sendMessage(update.getMessage(), helpProcessor.run());
                break;
            case START:
                messageService.sendMessage(buttonHandler.getButtonMainMenu(update.getMessage().getChatId(), startProcessor.run()));
                break;
            case SETTING:
                messageService.sendMessage(update.getMessage(), settingsProcessor.run());
                break;
            case NONE:
                messageService.sendMessage(update.getMessage(), noneProcessor.run());
                break;
            case ADD_WORD:
                messageService.sendMessage(update.getMessage(), "Введите слово (русский)");
                state = "ask_russian_word";
                break;
            case ADD_ENGLISH_WORD:
                messageService.sendMessage(update.getMessage(), "Введите перевод слова");
                state = "ask_english_word";
                break;
            case PROCESS_DONE:
                messageService.sendMessage(update.getMessage(), "Готово!");
                break;
            case FIND_WORD_BY_RUSSIAN:
                messageService.sendMessage(update.getMessage(), "Введите слово");
                state ="input_russian_word";
                break;

        }
    }

    private BotCommands getCommand(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message != null && message.hasText()) {
                String msgText = message.getText();
                if (msgText.startsWith(BotCommands.HELP.getCommand())) {
                    return BotCommands.HELP;
                } else if (msgText.startsWith(BotCommands.START.getCommand())) {
                    return BotCommands.START;
                } else if (msgText.startsWith(BotCommands.SETTING.getCommand())) {
                    return BotCommands.SETTING;
                } else if (msgText.startsWith("Добавить слово-перевод")){
                    return BotCommands.ADD_WORD;
                } else if(msgText.startsWith("Найти слово (вводить русское)")){
                    return BotCommands.FIND_WORD_BY_RUSSIAN;
                }else if (state.equals("ask_russian_word")){
                    word.setRussianWord(msgText);
                    wordService.saveWord(word);
                    return BotCommands.ADD_ENGLISH_WORD;
                } else if (state.equals("ask_english_word")){
                    word.setEnglishWord(msgText);
                    wordService.saveWord(word);
                    return BotCommands.PROCESS_DONE;
                } else if (state.equals("input_russian_word")) {
                    messageService.sendMessage(update.getMessage(), wordService.findByRussianWord(msgText));
                    return BotCommands.PROCESS_DONE;
                }
            }
        } else if (update.hasCallbackQuery()){
            CallbackQuery buttonQuery = update.getCallbackQuery();

        }
        return BotCommands.NONE;
    }

}
