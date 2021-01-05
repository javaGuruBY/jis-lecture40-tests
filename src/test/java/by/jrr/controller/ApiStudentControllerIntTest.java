package by.jrr.controller;

import by.jrr.bean.Student;
import by.jrr.config.SpringWebAppInitializer;
import by.jrr.config.WebConfig;
import by.jrr.service.StudentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringWebAppInitializer.class, WebConfig.class})
@WebAppConfiguration
public class ApiStudentControllerIntTest {
    @Mock
    StudentService studentServiceMock;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

//    @Test
//    public void saveNewStudent() {
//        studentController.saveNewStudent(new Student());
//        verify(studentServiceMock).saveNewStudent(any(Student.class));
//    }

    @Test
    public void findStudentById_shouldReturnStudent() throws Exception {

        mockMvc.perform(get("http://localhost:8077/api/studends/1"))
                .andDo(print());
//        long id = 5;
//        Student expected = makeStudent(id);
//
//        when(studentServiceMock.findStudentById(id))
//                .thenReturn(expected);
//
//        Student actual = studentController.findStudentById(id);
//        assertEquals(makeStudent(id), actual);
    }
//
//    @Test
//    public void findStudentById_shouldReturnEmptyStudent() {
//        long id = 10;
//        Student expected = new Student();
//
//        when(studentServiceMock.findStudentById(id))
//                .thenReturn(new Student());
//
//        Student actual = studentController.findStudentById(id);
//        assertEquals(expected, actual);
//    }

//    @Test
//    public void updateStudent() {
//        long id = 5;
//        Student student = makeStudent(id);
//        when(studentController.updateStudent(student))
//                .thenReturn(student);
//
//        Student actualUpdatedStudent = studentController.updateStudent(student);
//
//        assertEquals(student, actualUpdatedStudent);
//    }

//    @Test
//    public void deleteStudent() {
//        long id = 5;
//        Student student = makeStudent(id);
//
//        when(studentServiceMock.findStudentById(id))
//                .thenReturn(student);
//
//        studentController.deleteStudent(5L);
//
//        verify(studentServiceMock).findStudentById(5L);
//        verify(studentServiceMock).deleteStudent(student);
//    }

//    @Test
//    public void findAllStudents() {
//        Long[] ids = {1L, 2L, 3L};
//
//        List<Student> expected = Arrays.stream(ids)
//                .map(id -> makeStudent(id))
//                .collect(Collectors.toList());
//
//        when(studentController.findAllStudents())
//                .thenReturn(expected);
//
//        List<Student> actual = studentController.findAllStudents();
//
//        assertIterableEquals(expected, actual);
//    }

    private Student makeStudent(Long id) {
        Student student = new Student();
        student.setId(id);
        student.setName("studentName");
        student.setLastName("studentLastName");
        return student;
    }
}
