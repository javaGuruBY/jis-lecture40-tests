package by.jrr.service;

import by.jrr.bean.Student;
import by.jrr.config.DataBaseConfig;
import by.jrr.config.SpringWebAppInitializer;
import by.jrr.config.TestDBConfig;
import by.jrr.config.WebConfig;
import by.jrr.repository.StudentRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        SpringWebAppInitializer.class,
        WebConfig.class,
        DataBaseConfig.class,
        TestDBConfig.class,
        StudentRepository.class,
        StudentService.class
})
@WebAppConfiguration
@ActiveProfiles("tenStudents")
public class StudentServiceIntTest {

    @Autowired
    StudentService studentService;

    @Test
    public void findAllStudentsRegisteredYesterday() {
        studentService.setClock(Clock.fixed(Instant.parse("2020-12-31T00:00:00.00Z"), ZoneId.systemDefault()));
        List<Student> students = studentService.findAllStudentsRegisteredYesterday();
        assertEquals(1, students.size());
        assertEquals(9, (long) students.get(0).getId());
    }

//    @Test
//    public void testFindById_shouldReturn() {
//        Student student = studentRepository.findById(3L).get();
//        assertEquals(makeStudent(), student);
//    }

    @Test
    public void testFindById_shouldReturnEmpty() {

    }

    private Student makeStudent(Long id) {
        Student student = new Student();
        student.setId(id);
        student.setName("studentName");
        student.setLastName("studentLastName");
        student.setRegistrationStamp(LocalDateTime.parse("2020-05-11T00:00:00"));
        return student;
    }

    private Student makeStudent() {
        Student student = new Student();
        student.setId(3L);
        student.setName("student3");
        student.setLastName("last Name id3");
        student.setRegistrationStamp(LocalDateTime.parse("2020-05-11T00:00"));
        return student;
    }
}
