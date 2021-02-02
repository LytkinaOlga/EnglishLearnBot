package by.bntu.fitr.poisit.lytkina.englishLearnBot.service;

import by.bntu.fitr.poisit.lytkina.englishLearnBot.bean.User;
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
    @Autowired
    UserService userService;
    @Autowired
    User user;
    @Autowired
    ValidationService validationService;

    private String msgText;

    private static String state = "";
    private static String russianWord = "";
    private static String englishWord = "";
    private static boolean flag = false;

    public void dispatch(Update update) {
        user = userService.getCurrentUser(update);
        if (!userService.checkIfPersonDataExist(update.getMessage())){
            userService.saveUser(user);
        }

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
            case FIND_WORD_BY_ENGLISH:
                messageService.sendMessage(update.getMessage(), "Введите слово");
                state ="input_english_word";
                break;
            case RETURN_ENGLISH_WORD:
                messageService.sendMessage(update.getMessage(), wordService.findByRussianWord(update.getMessage().getText().toLowerCase(), user));
                break;
            case RETURN_RUSSIAN_WORD:
                messageService.sendMessage(update.getMessage(), wordService.findByEnglishWord(update.getMessage().getText().toLowerCase(), user));
                break;
            case RETURN_ALL_WORDS:
                messageService.sendMessage(update.getMessage(), wordService.findAllWords(user));
                break;
            case INCORRECT_INPUT:
                messageService.sendMessage(update.getMessage(), "Некорректный язык ввода");
                break;
        }
    }

    private BotCommands getCommand(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message != null && message.hasText()) {
                String msgText = message.getText().toLowerCase();
                if (msgText.startsWith(BotCommands.HELP.getCommand())) {
                    return BotCommands.HELP;
                } else if (msgText.startsWith(BotCommands.START.getCommand())) {
                    return BotCommands.START;
                } else if (msgText.startsWith(BotCommands.SETTING.getCommand())) {
                    return BotCommands.SETTING;
                } else if (msgText.startsWith("добавить слово-перевод")){
                    return BotCommands.ADD_WORD;
                } else if(msgText.startsWith("найти слово (вводить русское)")){
                    return BotCommands.FIND_WORD_BY_RUSSIAN;
                }else if(msgText.startsWith("найти слово (вводить английское)")) {
                    return BotCommands.FIND_WORD_BY_ENGLISH;
                }else if(msgText.startsWith("мой словарь")){
                    return BotCommands.RETURN_ALL_WORDS;
                }else if (state.equals("ask_russian_word")){
                    if (validationService.checkIfTextInRussian(msgText)){
                        word.setRussianWord(msgText);
                        return BotCommands.ADD_ENGLISH_WORD;
                    }else return BotCommands.INCORRECT_INPUT;


                } else if (state.equals("ask_english_word")){
                    if (validationService.checkIfTextInEnglish(msgText)){
                        word.setEnglishWord(msgText);
                        word.setUser(user);
                        wordService.saveWord(word);
                        return BotCommands.PROCESS_DONE;
                    }else return BotCommands.INCORRECT_INPUT;
                } else if (state.equals("input_russian_word")) {
                    return BotCommands.RETURN_ENGLISH_WORD;
                } else if (state.equals("input_english_word")){
                    return BotCommands.RETURN_RUSSIAN_WORD;
                }
            }
        } else if (update.hasCallbackQuery()){
            CallbackQuery buttonQuery = update.getCallbackQuery();

        }
        return BotCommands.NONE;
    }

}
