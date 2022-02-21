package ru.work.trainsheep;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.work.trainsheep.entity.User;
import ru.work.trainsheep.service.UserService;


@Controller
public class SimpleController {

    @Autowired
    private UserService userService;

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

    @PostMapping("/register")
    public String register(Model model, @RequestBody UserRegistrationData userRegistration) {
        if (userRegistration.email != null && userRegistration.password != null)
            model.addAttribute("status", "ok");
        else
            model.addAttribute("status", "fail");

        return "jsonTemplate";
    }

    @PostMapping("/addUser")
    public String addUser(Model model, @RequestBody UserData userData) {
        User user = User.from(userData);
        System.out.println("add user " + user);
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
