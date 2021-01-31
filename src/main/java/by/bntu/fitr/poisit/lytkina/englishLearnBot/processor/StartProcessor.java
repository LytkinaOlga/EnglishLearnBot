package by.bntu.fitr.poisit.lytkina.englishLearnBot.processor;

import org.springframework.stereotype.Service;

@Service
public class StartProcessor implements Processor {
    @Override
    public String run() {
        return "Hello! This bot help you improve your English vocabulary";
    }
}
