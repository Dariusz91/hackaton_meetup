package pl.jug.torun.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainPageController {

    @Value("${group.key:}")
    private String appKey;

    @RequestMapping("/draw")
    public String index() {
        return "mainPage/main";
    }

    @RequestMapping("/")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView("mainPage/login");
        mav.addObject("appKey", appKey);
        return mav;
    }
}
