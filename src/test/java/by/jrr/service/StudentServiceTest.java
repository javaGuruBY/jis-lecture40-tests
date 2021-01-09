package by.jrr.service;

import by.jrr.bean.Student;
import by.jrr.repository.StudentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StudentServiceTest {

    @InjectMocks
    StudentService studentService;
    @Mock
    StudentRepository studentRepositoryMock;

    @Test
    public void saveNewStudent() {
        studentService.saveNewStudent(new Student());
        verify(studentRepositoryMock).save(any(Student.class));
    }

    @Test
    public void findStudentById_shouldReturnStudent() {
        long id = 5;
        Optional<Student> expected = Optional.of(makeStudent(id));

        when(studentRepositoryMock.findById(id))
                .thenReturn(expected);

        Student actual = studentService.findStudentById(id);
        assertEquals(makeStudent(id), actual);
    }

    @Test
    public void findStudentById_shouldReturnEmptyStudent() {
        long id = 10;
        Student expected = new Student();

        when(studentRepositoryMock.findById(id))
                .thenReturn(Optional.empty());

        Student actual = studentService.findStudentById(id);
        assertEquals(expected, actual);
    }

    @Test
    public void updateStudent() {
        long id = 5;
        Student student = makeStudent(id);
        when(studentRepositoryMock.updateStudent(student))
                .thenReturn(student);

        Student actualUpdatedStudent = studentService.updateStudent(student);

        assertEquals(student, actualUpdatedStudent);
    }

    @Test
    public void deleteStudent() {
        long id = 5;
        Student student = makeStudent(id);

        studentService.deleteStudent(student);

        verify(studentRepositoryMock).delete(5L);
    }


    @Test
    public void findAllStudents() {
        Long[] ids = {1L, 2L, 3L};

        List<Student> expected = Arrays.stream(ids)
                .map(id -> makeStudent(id))
                .collect(Collectors.toList());

        when(studentRepositoryMock.findAll())
                .thenReturn(expected);

        List<Student> actual = studentService.findAllStudents();

        assertIterableEquals(expected, actual);
    }

    private Student makeStudent(Long id) {
        Student student = new Student();
        student.setId(id);
        student.setName("studentName");
        student.setLastName("studentLastName");
        return student;
    }
}
