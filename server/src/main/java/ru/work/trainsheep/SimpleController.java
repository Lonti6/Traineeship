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

}
