package by.bntu.fitr.poisit.lytkina.englishLearnBot.enums;

public enum  BotCommands {
    START("/start"),
    HELP("/help"),
    SETTING("/settings"),
    ADD_WORD("/add_word"),
    ADD_ENGLISH_WORD("/add_word_english"),
    PROCESS_DONE,
    FIND_WORD_BY_RUSSIAN,
    NONE("/none");

    String command;
    public String getCommand() {
        return command;
    }

    BotCommands(String command) {
        this.command = command;
    }
    BotCommands(){};
}
