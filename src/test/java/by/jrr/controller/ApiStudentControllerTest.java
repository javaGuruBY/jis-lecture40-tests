package by.jrr.controller;

import by.jrr.bean.Student;
import by.jrr.service.StudentService;
import org.junit.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApiStudentControllerTest {
    @InjectMocks
    ApiStudentController studentController;
    @Mock
    StudentService studentServiceMock;

    @Test
    public void saveNewStudent() {
        studentController.saveNewStudent(new Student());
        verify(studentServiceMock).saveNewStudent(any(Student.class));
    }

    @Test
    public void findStudentById_shouldReturnStudent() {
        long id = 5;
        Student expected = makeStudent(id);

        when(studentServiceMock.findStudentById(id))
                .thenReturn(expected);

        Student actual = studentController.findStudentById(id);
        assertEquals(makeStudent(id), actual);
    }

    @Test
    public void findStudentById_shouldReturnEmptyStudent() {
        long id = 10;
        Student expected = new Student();

        when(studentServiceMock.findStudentById(id))
                .thenReturn(new Student());

        Student actual = studentController.findStudentById(id);
        assertEquals(expected, actual);
    }

    @Test
    public void updateStudent() {
        long id = 5;
        Student student = makeStudent(id);
        when(studentController.updateStudent(student))
                .thenReturn(student);

        Student actualUpdatedStudent = studentController.updateStudent(student);

        assertEquals(student, actualUpdatedStudent);
    }

    @Test
    public void deleteStudent() {
        long id = 5;
        Student student = makeStudent(id);

        when(studentServiceMock.findStudentById(id))
                .thenReturn(student);

        studentController.deleteStudent(5L);

        verify(studentServiceMock).findStudentById(5L);
        verify(studentServiceMock).deleteStudent(student);
    }

    @Test
    public void findAllStudents() {
        Long[] ids = {1L, 2L, 3L};

        List<Student> expected = Arrays.stream(ids)
                .map(id -> makeStudent(id))
                .collect(Collectors.toList());

        when(studentController.findAllStudents())
                .thenReturn(expected);

        List<Student> actual = studentController.findAllStudents();

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
