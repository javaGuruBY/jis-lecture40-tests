package by.jrr.controller;

import by.jrr.bean.Student;
import by.jrr.config.MvcControllerTestBase;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("tenStudents")
public class StudentControllerIntTest extends MvcControllerTestBase {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void openIndex() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void findStudentById_shouldReturnStudent() throws Exception {

        MvcResult result = mockMvc.perform(get("/students/1"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(forwardedUrl("/WEB-INF/view/student.jsp"))
                .andExpect(view().name("student"))
                .andReturn();

        Map<String, Object> attributes = result.getModelAndView().getModel();

        Assertions.assertEquals(makeStudent(1L), attributes.get("student"));
    }

    private Student makeStudent(Long id) {
        Student student = new Student();
        student.setId(id);
        student.setName("student name");
        student.setLastName("last Name");
        return student;
    }
}
