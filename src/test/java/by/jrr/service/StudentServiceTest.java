package by.jrr.service;

import by.jrr.bean.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import by.jrr.repository.StudentRepository;

@RunWith(MockitoJUnitRunner.class)
public class StudentServiceTest {

    @InjectMocks
    StudentService studentService;
    @Mock
    StudentRepository repositoryMock;

    @Test
    public void saveNewStudent() {
        studentService.saveNewStudent(new Student());
        Mockito.verify(repositoryMock).save(Mockito.any(Student.class));
    }
}
