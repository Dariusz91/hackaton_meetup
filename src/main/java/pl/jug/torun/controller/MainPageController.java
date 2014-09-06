package pl.jug.torun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainPageController {

    @RequestMapping("/index")
    public String index() {
        return "mainPage/main";
    }

}
