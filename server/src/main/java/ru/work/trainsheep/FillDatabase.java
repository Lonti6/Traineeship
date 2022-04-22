package ru.work.trainsheep;

import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.work.trainsheep.entity.User;
import ru.work.trainsheep.send.UserRegistrationData;
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
        val userR = users.register(new UserRegistrationData(name, "", email, pass, isCompany));
        val user = users.findByEmail(email);
        user.setImage(image);
        users.save(user);
        if(userR != null)
            logger.info("create user [" + user.getId() + "] " + email + " " + pass + " " + name);
        return user;
    }

    @Override
    public void afterPropertiesSet() {

        createUser("Яндекс@ok.ru", "password", "Яндекс",
                "https://smartsellgroup.ru/wp-content/uploads/2021/12/stoimost-prodvizheniya-sajta-v-yandekse-3.jpg",
                true);
        createUser("СКБ Контур@ok.ru", "password", "СКБ Контур",
                "https://creativecallproject.ru/wp-content/uploads/2016/05/SKB.jpg",
                true);
        createUser("Mail.ru Group@ok.ru", "password", "Mail.ru Group",
                "https://inopiter.ru/wp-content/uploads/2021/05/mail.ru-group.jpg",
                true);
        createUser("Skyeng@ok.ru", "password", "Skyeng",
                "https://mir-s3-cdn-cf.behance.net/project_modules/fs/7707ad94913247.5e8b5149e560c.png",
                true);

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



        int k = 20;
        for (int i = 0; i < k; i++) {
            val name = generator.getRandom(generator.companies);
            val user = createUser(name + "@ok.ru", "password", name, generator.getRandom(generator.icons), true);
            notes.createAndSave(user, generator.generateVacancyNote(List.of()));
        }
        val userList =  users.getAll();
        logger.info("create " + k +" notes. OK!");

//        k = 100;
//        int count = 0;
//        for (int i = 0; i < k; i++) {
//            val user1 = generator.getRandom(userList);
//            val user2 = generator.getRandom(userList);
//
//            if (user1 != user2){
//                chatService.sendMessage(user1, user2, generator.getRandomMessageText());
//                count ++;
//            }
//        }
//        logger.info("create " + count +" messages. OK!");


    }
}
