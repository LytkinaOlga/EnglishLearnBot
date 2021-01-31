package by.bntu.fitr.poisit.lytkina.englishLearnBot.processor;

import org.springframework.stereotype.Service;

@Service
public class HelpProcessor implements Processor{
    @Override
    public String run() {
        return "Hello from help processor";
    }
}
