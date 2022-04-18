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
import ru.work.trainsheep.send.ChatRequest;
import ru.work.trainsheep.send.UserData;
import ru.work.trainsheep.send.UserRegistrationData;
import ru.work.trainsheep.send.VacancyRequest;
import ru.work.trainsheep.service.ChatService;
import ru.work.trainsheep.service.NotesService;
import ru.work.trainsheep.service.UserService;


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

    @PostMapping("/adverts")
    public String adverts(Model model, @RequestBody VacancyRequest advertRequest) {
        if (advertRequest != null) {
            val result = notesService.getAdvertResult(advertRequest);
            model.addAttribute("status", "ok");
            model.addAttribute("result", result);
        }
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
            log.info("messages " + userPass.getUsername() + " to " + request);
            val user = userService.findByEmail(userPass.getUsername());
            val oldUser = userService.findByEmail(request.getEmail());

            model.addAttribute("status", "ok");
            model.addAttribute("messages", chatService.getMessages(user, oldUser, request.getPage(), request.getCountMessageOnPage()));
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
            userService.register(user.getEmail(), user.getPassword(), user.getName());
            model.addAttribute("status", "ok");
            model.addAttribute("name", user.getName());
        } else
            model.addAttribute("status", "fail");

        return "jsonTemplate";
    }

    @PostMapping("/addUser")
    public String addUser(Model model, @RequestBody UserData userData) {
        User user = User.from(userData);
        boolean status = userService.addUser(user);
        if (status) {
            model.addAttribute("status", "ok");
            model.addAttribute("user", user);
        } else {
            model.addAttribute("status", "fail");
        }
        return "jsonTemplate";
    }

}
