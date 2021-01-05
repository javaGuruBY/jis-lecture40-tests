package by.jrr.controller;

import by.jrr.bean.Student;
import by.jrr.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApiStudentController {

    @Autowired
    StudentService studentService;

    @PostMapping("/api/studends")
    public void saveNewStudent(Student student) {
        studentService.saveNewStudent(student);
    }

    @GetMapping("/api/studends/{id}")
    public Student findStudentById(@PathVariable Long id) {
        return studentService.findStudentById(id);
    }

    @PutMapping("/api/studends/")
    public Student updateStudent(Student student) {
        return studentService.updateStudent(student);
    }

    @DeleteMapping("/api/studends/{id}")
    public void deleteStudent(@PathVariable Long id) {
        Student student = studentService.findStudentById(id);
        studentService.deleteStudent(student);
    }

    @GetMapping("/api/studends")
    public List<Student> findAllStudents() {
        return studentService.findAllStudents();
    }
}
