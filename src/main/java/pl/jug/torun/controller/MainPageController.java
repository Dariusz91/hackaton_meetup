package pl.jug.torun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainPageController {

    @RequestMapping("/draw")
    public String index() {
        return "mainPage/main";
    }

    @RequestMapping("/")
    public String login() {
        return "mainPage/login";
    }
}
