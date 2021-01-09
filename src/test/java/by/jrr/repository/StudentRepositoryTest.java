package by.jrr.repository;

import by.jrr.bean.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class StudentRepositoryTest {

    @InjectMocks
    StudentRepository studentRepository;
    @Mock
    JdbcTemplate jdbcTemplate;

    @Test
    public void save() {
        long id = 5;
        studentRepository.save(makeStudent(id));
        String query = "INSERT INTO student values (null, 'studentName', 'studentLastName', '2020-05-11T00:00')";
        verify(jdbcTemplate).execute(query);
    }

    @Test
    public void findById() {
    }

    @Test
    public void findAll() {
    }

    @Test
    public void updateStudent() {
    }

    @Test
    public void delete() {
    }

    private Student makeStudent(Long id) {
        Student student = new Student();
        student.setId(id);
        student.setName("studentName");
        student.setLastName("studentLastName");
        student.setRegistrationStamp(LocalDateTime.parse("2020-05-11T00:00:00"));
        return student;
    }
}
