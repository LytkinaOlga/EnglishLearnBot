package by.bntu.fitr.poisit.lytkina.englishLearnBot.service;

import by.bntu.fitr.poisit.lytkina.englishLearnBot.bean.User;
import by.bntu.fitr.poisit.lytkina.englishLearnBot.repo.UserRepoI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class UserService {

    @Autowired
    UserRepoI userRepository;
    @Autowired
    User user;

    public boolean checkIfPersonDataExist(Message message) {
        if (userRepository.existsById(message.getFrom().getId())) {
            return true;
        } else return false;
    }

    void saveUser(User user){
        userRepository.save(user);
    }

    User getCurrentUser(Update update){
        user.setId(update.getMessage().getFrom().getId());
        user.setName(update.getMessage().getFrom().getFirstName() + " " + update.getMessage().getFrom().getLastName());
        return user;
    }

}
