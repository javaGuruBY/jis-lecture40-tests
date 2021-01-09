package by.jrr.controller;

import by.jrr.bean.Student;
import by.jrr.config.MvcControllerTestBase;
import by.jrr.config.SpringWebAppInitializer;
import by.jrr.config.WebConfig;
import by.jrr.service.StudentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {
        SpringWebAppInitializer.class,
        WebConfig.class,
        StudentController.class,
})
public class StudentControllerIntTestWithServiceMocked extends MvcControllerTestBase {

    @Mock
    StudentService studentServiceMock;

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        wac.getBean(StudentController.class).studentService = studentServiceMock;
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void openIndexWithMock() throws Exception {
        when(studentServiceMock.findStudentById(1L))
                .thenReturn(new Student());

        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void findStudentById_shouldReturnStudent() throws Exception {
        //bad that mocked and expected created with same makeStudent();
        when(studentServiceMock.findStudentById(5L))
                .thenReturn(makeStudent(5L));

        MvcResult result = mockMvc.perform(get("/students/5"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(forwardedUrl("/WEB-INF/view/student.jsp"))
                .andExpect(view().name("student"))
                .andReturn();

        Map<String, Object> attributes = result.getModelAndView().getModel();

        Assertions.assertEquals(makeStudent(5L), attributes.get("student"));
    }

    private Student makeStudent(Long id) {
        Student student = new Student();
        student.setId(id);
        student.setName("stubbed student name");
        student.setLastName("stubbed last Name");
        return student;
    }
}
