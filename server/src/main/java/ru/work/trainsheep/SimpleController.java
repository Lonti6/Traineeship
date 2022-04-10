package ru.work.trainsheep;


import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.work.trainsheep.entity.User;
import ru.work.trainsheep.entity.Role;
import ru.work.trainsheep.entity.UserPasswords;
import ru.work.trainsheep.repository.UserPasswordRepository;
import ru.work.trainsheep.send.AdvertRequest;
import ru.work.trainsheep.send.UserData;
import ru.work.trainsheep.send.UserRegistrationData;
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
    PasswordEncoder passwordEncoder;

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
    public String adverts(Model model, @RequestBody AdvertRequest advertRequest) {
        if (advertRequest != null) {
            val result = notesService.getAdvertResult(advertRequest);
            model.addAttribute("status", "ok");
            model.addAttribute("result", result);
        }
        return "jsonTemplate";
    }

    @PostMapping("/login")
    public String register(Model model, Authentication authentication) {
        if(authentication != null) {
            model.addAttribute("status", "ok");
        } else
            model.addAttribute("status", "fail");
        return "jsonTemplate";
    }

    @PostMapping("/register")
    public String register(Model model, @RequestBody UserRegistrationData user) {


        if (user.getEmail() != null && user.getPassword() != null && userPasswordRepository.findByUsername(user.getEmail()) == null) {
            val userPass = new UserPasswords(user.getEmail(), passwordEncoder.encode(user.getPassword()), Role.USER.toString());
            userPasswordRepository.save(userPass);
            model.addAttribute("status", "ok");
            model.addAttribute("user", new UserRegistrationData(user.getEmail(), null));
        }
        else
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
