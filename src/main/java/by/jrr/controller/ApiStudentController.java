package by.jrr.controller;

import by.jrr.bean.Student;
import by.jrr.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApiStudentController {

    @Autowired
    StudentService studentService;

    @PostMapping("/api/students")
    public void saveNewStudent(@RequestBody Student student) {
        studentService.saveNewStudent(student);
    }

    @GetMapping(value = "/api/students/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Student findStudentById(@PathVariable Long id) {
        return studentService.findStudentById(id);
    }

    @PutMapping("/api/students/")
    public Student updateStudent(Student student) {
        return studentService.updateStudent(student);
    }

    @DeleteMapping("/api/students/{id}")
    public void deleteStudent(@PathVariable Long id) {
        Student student = studentService.findStudentById(id);
        studentService.deleteStudent(student);
    }

    @GetMapping("/api/students")
    public List<Student> findAllStudents() {
        return studentService.findAllStudents();
    }
}
