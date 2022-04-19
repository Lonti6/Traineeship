package ru.work.trainsheep;

import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.work.trainsheep.entity.User;
import ru.work.trainsheep.service.ChatService;
import ru.work.trainsheep.service.NotesService;
import ru.work.trainsheep.service.PasswordsService;
import ru.work.trainsheep.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Component
public class FillDatabase implements InitializingBean {

    @Autowired
    NotesService notes;

    @Autowired
    UserService users;

    @Autowired
    PasswordsService passwords;

    @Autowired
    ChatService chatService;

    DataGenerator generator = new DataGenerator();

    Logger logger = LoggerFactory.getLogger(getClass());

    private User createUser(String email, String pass, String name, String image, boolean isCompany){
        users.register(email, pass, name);
        val user = users.findByEmail(email);
        user.setImage(image);
        user.setCompany(isCompany);
        users.save(user);
        logger.info("create user " + email + " " + pass + " " + name);
        return user;
    }

    @Override
    public void afterPropertiesSet() {

        createUser("admin@admin.ru",
                "password",
                "Админ",
                "https://ru.freepik.com/free-photo/rear-view-of-programmer-working-all-night-long_5698334.htm#query=system%20administrator&position=1&from_view=keyword",
                false);
        createUser(
                "nikita@ok.ru",
                "password",
                "Никита",
                "https://www.pngitem.com/pimgs/m/177-1772631_psyduck-png-9-png-image-psyduck-pokemon.png", false);
        createUser("diana@ya.ru", "password", "Анна", "https://cs5.livemaster.ru/storage/8d/58/15676c2e3677f826dd7aac5a50yl--materialy-dlya-tvorchestva-organza-shelkovaya-chernaya.jpg", false);
        createUser("okki@lev.ru", "nopassword", "Лев", "https://yt3.ggpht.com/a/AATXAJxxVTIrOzmAMHijjkT57-UJ83SSYlnLrlodfA=s900-c-k-c0xffffffff-no-rj-mo", false);

        val userList =  users.getAll();

        int k = 20;
        for (int i = 0; i < k; i++) {
            val name = generator.getRandom(generator.companies);
            val user = createUser(name + "@ok.ru", "password", name, generator.getRandom(generator.icons), true);
            notes.createAndSave(user, generator.generateVacancyNote(List.of()));
        }

        logger.info("create " + k +" notes. OK!");

        k = 40;
        int count = 0;
        for (int i = 0; i < k; i++) {
            val user1 = generator.getRandom(userList);
            val user2 = generator.getRandom(userList);

            if (user1 != user2){
                chatService.sendMessage(user1, user2, generator.getRandomMessageText());
                count ++;
            }
        }
        logger.info("create " + count +" messages. OK!");


//        notes.findAll().forEach((ent) -> {
//            System.out.println(ent);
//            System.out.print("    ");
//            for(Tag tag : ent.getTags()){
//                System.out.print(tag + " ");
//            }
//            System.out.println();
//        });
    }
}
