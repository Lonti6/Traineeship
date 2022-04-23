package ru.work.trainsheep;

import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.work.trainsheep.entity.UserPasswords;
import ru.work.trainsheep.send.SetNotification;
import ru.work.trainsheep.send.VacancyRequest;
import ru.work.trainsheep.service.UserService;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Controller
public class NotificationController {

    Logger log = LoggerFactory.getLogger(getClass());

    private boolean notification = false;
    private Set<Long> usersNotifications = new CopyOnWriteArraySet<>();


    @PostMapping("/getNotification")
    public String getNotification(Model model, Authentication authentication) {
        if (authentication != null) {
            val userPass = (UserPasswords) authentication.getPrincipal();
            if (notification)
                usersNotifications.add(userPass.getId());

            model.addAttribute("status", "ok");
            model.addAttribute("result", notification);
            model.addAttribute("count", usersNotifications.size());
        } else
            model.addAttribute("status", "fail");
        return "jsonTemplate";
    }

    @PostMapping("/setNotification")
    public String setNotification(Model model, Authentication authentication, @RequestBody SetNotification notifi) {
        if (authentication != null && notifi != null) {
            val userPass = (UserPasswords) authentication.getPrincipal();
            notification = notifi.isNotification();
            if (!notification)
                usersNotifications.clear();

            model.addAttribute("status", "ok");
            model.addAttribute("result", notification);
            model.addAttribute("count", usersNotifications.size());
        } else
            model.addAttribute("status", "fail");
        return "jsonTemplate";
    }

}
