package by.jrr.service;

import by.jrr.bean.Student;
import by.jrr.repository.StudentRepository;

import java.net.PortUnreachableException;
import java.time.LocalDateTime;

public class StudentService {

    StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void saveNewStudent(Student student) {
        student.setRegistrationStamp(LocalDateTime.now());
        studentRepository.save(student);
    }

    public Student findStudentById(Long id) {
        return studentRepository.findById(id).orElseGet(Student::new);
    }
}
