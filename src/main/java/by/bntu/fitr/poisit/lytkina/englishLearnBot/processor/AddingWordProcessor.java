package by.bntu.fitr.poisit.lytkina.englishLearnBot.processor;

import org.springframework.stereotype.Service;

@Service
public class AddingWordProcessor implements Processor{
    @Override
    public String run() {
        return "Отправь мне слово в формате СЛОВО – ПЕРЕВОД, и я добавлю их в твой словарь на изучение. Например \uD83D\uDC47\n" +
                "\n" +
                "car машина\n";
    }
}
