package by.bntu.fitr.poisit.lytkina.englishLearnBot.processor;

import org.springframework.stereotype.Service;

@Service
public class NoneProcessor implements Processor{
    @Override
    public String run() {
        return "Hello from None processor";
    }
}
