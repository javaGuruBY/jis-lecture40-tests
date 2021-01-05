package by.jrr.repository;

import by.jrr.bean.Student;
import by.jrr.config.DataBaseConfig;
import by.jrr.config.SpringWebAppInitializer;
import by.jrr.config.TestDBConfig;
import by.jrr.config.WebConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        SpringWebAppInitializer.class,
        WebConfig.class,
        DataBaseConfig.class,
        TestDBConfig.class,
        StudentRepository.class
})
@WebAppConfiguration
@ActiveProfiles("tenStudents")
public class StudentRepositoryIntTest {

    @Autowired
    StudentRepository studentRepository;

    @Test
    public void findAll() {
        System.out.println(studentRepository.findAll().size());
    }

    @Test
    public void save() {

        studentRepository.save(makeStudent(null));
        Student student = studentRepository.findById(11L).get();
        Student expectedStudent = makeStudent(11L);
        Assert.assertEquals(expectedStudent, student);

        List<Student> students = studentRepository.findAll();
        assertEquals(11, students.size());
    }


    @Test
    public void testFindById_shouldReturn() {
        Student student = studentRepository.findById(3L).get();
        assertEquals(makeStudent(), student);
    }

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
