package by.jrr.controller;

import by.jrr.bean.Student;
import by.jrr.repository.StudentRepository;
import by.jrr.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class StudentController {

    @Autowired
    StudentService studentService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String openIndex(Model model) {
        Student student = studentService.findStudentById(1L);
        model.addAttribute("message", "Hello from spring MVC");
        model.addAttribute("student", student);
        return "home";
    }
}
