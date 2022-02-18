package ru.work.trainsheep;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class SimpleController {


    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("status", "ok");
        return "jsonTemplate";
    }

    @GetMapping("/user")
    public String user(Model model){
        model.addAttribute("status", "ok");
        return "jsonTemplate";
    }

    @PostMapping("/register")
    public String register(Model model, @RequestBody UserRegistrationDto userRegistration){
        if(userRegistration.email != null && userRegistration.password != null)
            model.addAttribute("status", "ok");
        else
            model.addAttribute("status", "fail");

        return "jsonTemplate";
    }

}
