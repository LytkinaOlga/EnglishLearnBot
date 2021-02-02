package by.bntu.fitr.poisit.lytkina.englishLearnBot.processor;

import org.springframework.stereotype.Service;

@Service
public class StartProcessor implements Processor {
    @Override
    public String run() {
        return "Привет! Теперь ты можешь создать свой персональный словарь. Добавляй слова и я помогу тебе их выучить:) Увидимся на тренировках!";
    }
}
