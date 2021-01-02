package by.jrr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class StudentController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String openIndex(Model model) {
        model.addAttribute("message", "Hello from spring MVC");
        return "home";
    }
}
