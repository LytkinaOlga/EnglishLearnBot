package by.bntu.fitr.poisit.lytkina.englishLearnBot.service;

import by.bntu.fitr.poisit.lytkina.englishLearnBot.bean.User;
import by.bntu.fitr.poisit.lytkina.englishLearnBot.bean.Word;
import by.bntu.fitr.poisit.lytkina.englishLearnBot.buttonHandler.ButtonHandler;
import by.bntu.fitr.poisit.lytkina.englishLearnBot.enums.BotCommands;
import by.bntu.fitr.poisit.lytkina.englishLearnBot.processor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;

@Service
public class RequestDispatcher {

    @Autowired
    MessageService messageService;
    @Autowired
    StartProcessor startProcessor;
    @Autowired
    AddingWordProcessor addingWordProcessor;
    @Autowired
    HelpProcessor helpProcessor;
    @Autowired
    SettingsProcessor settingsProcessor;
    @Autowired
    NoneProcessor noneProcessor;
    @Autowired
    ButtonHandler buttonHandler;

    @Autowired
    WordService wordService;
    @Autowired
    UserService userService;
    @Autowired
    User user;
    @Autowired
    Word word;
    @Autowired
    ValidationService validationService;
    @Autowired
    TrainingService trainingService;

    private String msgText;

    private static String state = "";
    private static String russianWord = "";
    private static String englishWord = "";
    private static boolean flag = false;

    public void dispatch(Update update) throws InterruptedException {

        user = userService.getCurrentUser(update);
        if (!userService.checkIfPersonDataExist(update)){
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
                messageService.sendMessage(update.getMessage(), addingWordProcessor.run());
                state = "ask_word";
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
                messageService.sendMessage(update.getMessage(), wordService.findAllWordsReturnString(user));
                break;
            case INCORRECT_INPUT:
                messageService.sendMessage(update.getMessage(), "Некорректный ввод");
                break;
            case TRAINING_RUSSIAN_ENGLISH:
                if (wordService.checkIfExistEnoughWordsForTraining(wordService.findAllWordsReturnList(user))){
                    trainingService.trainRussianEnglish(user, update);
                }else messageService.sendMessage(update.getMessage(), "У вас мало слов в словаре. Добавьте еще! (Минимум - 3)");

                break;
            case TRAINING_ENGLISH_RUSSIAN:
                if (wordService.checkIfExistEnoughWordsForTraining(wordService.findAllWordsReturnList(user))){
                    trainingService.trainEnglishRussian(user, update);
                }else messageService.sendMessage(update.getMessage(), "У вас мало слов в словаре. Добавьте еще! (Минимум - 3)");

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
                }else if(msgText.startsWith("тренировка (русский - английский)")){
                    return BotCommands.TRAINING_RUSSIAN_ENGLISH;
                }else if(msgText.startsWith("тренировка (английский - русский)")){
                    return BotCommands.TRAINING_ENGLISH_RUSSIAN;
                }
                else if (state.equals("ask_word")){
                    word = validationService.returnWord(msgText);
                    word.setId(null);
                    word.setUser(user);

                    if (word.getRussianWord() == null || word.getEnglishWord() == null){
                        return BotCommands.INCORRECT_INPUT;

                    }else {wordService.saveWord(word);
                    return BotCommands.PROCESS_DONE;}

                } else if (state.equals("input_russian_word")) {
                    return BotCommands.RETURN_ENGLISH_WORD;
                } else if (state.equals("input_english_word")){
                    return BotCommands.RETURN_RUSSIAN_WORD;
                }
            }
        } else if (update.hasCallbackQuery()){
            CallbackQuery buttonQuery = update.getCallbackQuery();
            if (buttonQuery.getData().equals("RightAnswer")) {
                messageService.sendMessage( update.getCallbackQuery().getMessage(),"Правильно!");
                return BotCommands.TRAINING_RUSSIAN_ENGLISH;
            } else if (buttonQuery.getData().equals("WrongAnswer")){
               messageService.sendMessage( update.getCallbackQuery().getMessage(), "Неправильно!!");
                return BotCommands.TRAINING_RUSSIAN_ENGLISH;
            }

        }
        return BotCommands.NONE;
    }

}
