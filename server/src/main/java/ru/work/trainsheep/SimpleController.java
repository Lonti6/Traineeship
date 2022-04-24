package ru.work.trainsheep;


import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.work.trainsheep.entity.Role;
import ru.work.trainsheep.entity.User;
import ru.work.trainsheep.entity.UserPasswords;
import ru.work.trainsheep.repository.UserPasswordRepository;
import ru.work.trainsheep.send.*;
import ru.work.trainsheep.service.ChatService;
import ru.work.trainsheep.service.NotesService;
import ru.work.trainsheep.service.UserService;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;


@Controller
public class SimpleController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserPasswordRepository userPasswordRepository;

    @Autowired
    private NotesService notesService;

    @Autowired
    private ChatService chatService;

    Logger log = LoggerFactory.getLogger(getClass());


    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("status", "ok");
        return "jsonTemplate";
    }

    @GetMapping("/user")
    public String user(Model model) {
        model.addAttribute("status", "ok");
        return "jsonTemplate";
    }

    @PostMapping("/vacancies")
    public String vacancies(Model model, @RequestBody VacancyRequest vacancyRequest) {
        if (vacancyRequest != null) {
            val result = notesService.getVacancyResult(vacancyRequest);
            model.addAttribute("status", "ok");
            model.addAttribute("result", result);
        }
        return "jsonTemplate";
    }
    @PostMapping("/vacanciesWithAuth")
    public String vacanciesWithAuth(Model model, Authentication authentication, @RequestBody VacancyRequest vacancyRequest) {
        if (authentication != null && vacancyRequest != null) {
            val userPass = (UserPasswords) authentication.getPrincipal();
            val user = userService.findByEmail(userPass.getUsername());
            val result = notesService.getVacancyResultWithLogin(vacancyRequest, user);
            log.info("search (" + vacancyRequest.getText() + ") ("  + vacancyRequest.getEmailFilter() + ")" );
            model.addAttribute("status", "ok");
            model.addAttribute("result", result);
        } else
            model.addAttribute("status", "fail");
        return "jsonTemplate";
    }

    @PostMapping("/favoriteVacancies")
    public String favoriteVacancies(Model model, Authentication authentication, @RequestBody VacancyRequest vacancyRequest) {
        if (authentication != null && vacancyRequest != null) {
            val userPass = (UserPasswords) authentication.getPrincipal();
            val user = userService.findByEmail(userPass.getUsername());
            val result = notesService.getFavoriteVacancies(vacancyRequest, user);
            log.info("get favorites vacancies " + vacancyRequest + " [" + result.getNotes().size() + " ]");
            model.addAttribute("status", "ok");
            model.addAttribute("result", result);
        } else
            model.addAttribute("status", "fail");
        return "jsonTemplate";
    }

    @PostMapping("/setFavoriteVacancy")
    public String setFavoriteVacancy(Model model, Authentication authentication, @RequestBody SetFavoriteVacancyRequest request) {
        if (authentication != null && request != null) {
            val userPass = (UserPasswords) authentication.getPrincipal();
            val user = userService.findByEmail(userPass.getUsername());
            val vacancy = notesService.setFavorite(user, request);
            log.info("set favorite vacancy " + user.getEmail() + " " + user.getFirstName() + " " + vacancy.isFavorite());
            model.addAttribute("status", "ok");
            model.addAttribute("vacancy", vacancy);
        } else
            model.addAttribute("status", "fail");
        return "jsonTemplate";
    }

    @PostMapping("/login")
    public String login(Model model, Authentication authentication) {
        if (authentication != null) {
            val userPass = (UserPasswords) authentication.getPrincipal();
            val user = userService.findByEmail(userPass.getUsername());
            model.addAttribute("status", "ok");
            model.addAttribute("name", user.getFirstName());
        } else
            model.addAttribute("status", "fail");
        return "jsonTemplate";
    }

    @PostMapping("/messages")
    public String messages(Model model, Authentication authentication, @RequestBody ChatRequest request) {
        if (authentication != null && request != null) {
            val userPass = (UserPasswords) authentication.getPrincipal();
            //log.info("messages " + userPass.getUsername() + " to " + request);
            val user = userService.findByEmail(userPass.getUsername());
            val oldUser = userService.findByEmail(request.getEmail());
            val messages = chatService.getMessages(user, oldUser, request.getPage(), request.getCountMessageOnPage());
           // log.info("messages size" + messages.getMessages().size() + " " + messages);
            model.addAttribute("status", "ok");
            model.addAttribute("messages", messages);
        } else
            model.addAttribute("status", "fail");
        return "jsonTemplate";
    }

    @PostMapping("/update-user")
    public String updateUser(Model model, Authentication authentication, @RequestBody UserData request) {
        if (authentication != null && request != null) {
            val userPass = (UserPasswords) authentication.getPrincipal();
//            log.info("send messages from " + userPass.getUsername() + " to " + request);
            val user = userService.findByEmail(userPass.getUsername());
            userService.updateUser(user, request);

            model.addAttribute("status", "ok");
            model.addAttribute("user", user.toUserDate());
        } else
            model.addAttribute("status", "fail");
        return "jsonTemplate";
    }

    @PostMapping("/get-user")
    public String getUser(Model model, Authentication authentication, @RequestBody UserDataRequest request) {
        if (authentication != null && request != null) {
            val userPass = (UserPasswords) authentication.getPrincipal();
            val user = userService.findByEmail(request.getEmail());
            log.info("get user " + user);

            model.addAttribute("status", "ok");
            model.addAttribute("user", user.toUserDate());
        } else
            model.addAttribute("status", "fail");
        return "jsonTemplate";
    }

    @PostMapping("/get-companies")
    public String getCompanies(Model model, Authentication authentication, @RequestBody CompanyRequest request) {

        if (authentication != null && request != null) {
            val userPass = (UserPasswords) authentication.getPrincipal();
//            log.info("send messages from " + userPass.getUsername() + " to " + request);

            model.addAttribute("status", "ok");
            model.addAttribute("result", userService.getCompanies(request));
        } else
            model.addAttribute("status", "fail");
        return "jsonTemplate";
    }

    @PostMapping("/send-message")
    public String messages(Model model, Authentication authentication, @RequestBody SendMessageRequest request) {
        if (authentication != null && request != null) {
            val userPass = (UserPasswords) authentication.getPrincipal();
//            log.info("send messages from " + userPass.getUsername() + " to " + request);
            val user = userService.findByEmail(userPass.getUsername());
            val oldUser = userService.findByEmail(request.getEmail());

            model.addAttribute("status", "ok");
            model.addAttribute("message", chatService.sendMessage(user, oldUser, request.getText()));
        } else
            model.addAttribute("status", "fail");
        return "jsonTemplate";
    }
    @PostMapping("/search-chats")
    public String messages(Model model, Authentication authentication, @RequestBody SearchChatsRequest request) {
        if (authentication != null && request != null) {
            val userPass = (UserPasswords) authentication.getPrincipal();
//            log.info("send messages from " + userPass.getUsername() + " to " + request);
            val user = userService.findByEmail(userPass.getUsername());
            val list = userService.findUsersForChat(user, request);

            model.addAttribute("status", "ok");
            model.addAttribute("chats", list);
        } else
            model.addAttribute("status", "fail");
        return "jsonTemplate";
    }

    @PostMapping("/chats")
    public String chats(Model model, Authentication authentication) {
        if (authentication != null) {
            val userPass = (UserPasswords) authentication.getPrincipal();

            model.addAttribute("status", "ok");
            model.addAttribute("chats", chatService.getChats(userPass.getUsername()));
        } else
            model.addAttribute("status", "fail");
        return "jsonTemplate";
    }



    @PostMapping("/register")
    public String register(Model model, @RequestBody UserRegistrationData user) {


        if (user.getEmail() != null && user.getPassword() != null && userPasswordRepository.findByUsername(user.getEmail()) == null) {
            userService.register(user);
            model.addAttribute("status", "ok");
            model.addAttribute("name", user.getName());
        } else
            model.addAttribute("status", "fail");

        return "jsonTemplate";
    }


    @PostMapping("/removeVacancy")
    public String removeVacancy(Model model, Authentication authentication, @RequestBody VacancyNote note) {
        if (authentication != null) {
            val userPass = (UserPasswords) authentication.getPrincipal();

            log.info("remove vacancy " + note);
            notesService.remove(note);

            model.addAttribute("status", "ok");
            model.addAttribute("result", "ok");
        } else
            model.addAttribute("status", "fail");
        return "jsonTemplate";
    }


    @PostMapping("/createVacancy")
    public String createVacancy(Model model, Authentication authentication, @RequestBody VacancyNote note) {
        if (authentication != null) {
            val userPass = (UserPasswords) authentication.getPrincipal();
            val user = userService.findByEmail(userPass.getUsername());
            val e = notesService.createAndSave(user, note);
            //log.info("create vacancy " + e);

            model.addAttribute("status", "ok");
            model.addAttribute("vacancy", e);
        } else
            model.addAttribute("status", "fail");
        return "jsonTemplate";
    }

}
