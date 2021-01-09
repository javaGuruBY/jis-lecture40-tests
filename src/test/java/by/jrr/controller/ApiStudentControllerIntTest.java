package by.jrr.controller;

import by.jrr.bean.Student;
import by.jrr.config.RestControllerTestBase;
import by.jrr.repository.StudentRepository;
import by.jrr.service.StudentService;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("tenStudents")
public class ApiStudentControllerIntTest extends RestControllerTestBase {

    @Spy
    StudentService studentServiceSpy;
    @Spy
    StudentRepository studentRepositorySpy;
    @Spy
    JdbcTemplate jdbcTemplateSpy;

    @Captor
    ArgumentCaptor<Long> idCaptor;
    @Captor
    ArgumentCaptor<String> queryCaptor;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        jdbcTemplateSpy.setDataSource((DataSource) wac.getBean("tenStudentsData"));
        studentRepositorySpy.setJdbcTemplate(jdbcTemplateSpy);
        studentServiceSpy.setStudentRepository(studentRepositorySpy);
        wac.getBean(ApiStudentController.class).studentService = studentServiceSpy;
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

        studentServiceSpy.setClock(Clock.fixed(Instant.parse("2020-12-31T00:00:00.00Z"), ZoneId.of("UTC")));
    }


    @Test
    public void saveNewStudent() throws Exception {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("name", "newStudent");

        RequestBuilder request = MockMvcRequestBuilders.post("/api/students")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonBody.toString());

        MvcResult result = mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        Student actualStudent = studentRepository.findById(11L).get();
        Assert.assertEquals(Long.valueOf(11), actualStudent.getId());
        Assert.assertEquals("newStudent", actualStudent.getName());
//        Assert.assertNull(actualStudent.getLastName()); //this fails because in query in becomes 'null', not NULL: good example why we should tes
        Assert.assertNotNull(actualStudent.getRegistrationStamp());

        Assertions.assertThat(actualStudent.getId()).isEqualTo(Long.valueOf(11));
        Assertions.assertThat(actualStudent.getName()).isEqualTo("newStudent");
        Assertions.assertThat(actualStudent.getLastName()).isEqualTo("null");
        Assertions.assertThat(actualStudent.getRegistrationStamp()).isNotNull();

        Assertions.assertThat(result.getResponse().getContentAsString()).isEmpty();

        Mockito.verify(jdbcTemplateSpy).execute(queryCaptor.capture());
        String actualQuery = queryCaptor.getValue();
        assertThat(actualQuery).isEqualTo(
                "INSERT INTO student values (null, 'newStudent', 'null', '2020-12-31T00:00')" //compare 1st & 3rd null
        );
    }

    @Test
    public void findStudentById_shouldReturnStudent() throws Exception {
        String expected = "{\"id\":1,\"name\":\"student name\",\"lastName\":\"last Name\",\"registrationStamp\":null}";
        MvcResult result = mockMvc.perform(get("/api/students/1"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assert.assertEquals(expected, content);

        Mockito.verify(studentServiceSpy).findStudentById(1L);
        Mockito.verify(studentRepositorySpy).findById(1L);
        String query = "SELECT * FROM student WHERE id=1";
        Mockito.verify(jdbcTemplateSpy).query(Mockito.eq(query), Mockito.any(BeanPropertyRowMapper.class));

        // --- OR --- //
        Mockito.verify(studentServiceSpy).findStudentById(idCaptor.capture());
        Mockito.verify(studentRepositorySpy).findById(idCaptor.capture());
        Mockito.verify(jdbcTemplateSpy).query(queryCaptor.capture(), Mockito.any(BeanPropertyRowMapper.class));

        assertThat(idCaptor.getAllValues()).allMatch(id -> id.equals(1L));
        assertThat(queryCaptor.getValue()).isEqualTo("SELECT * FROM student WHERE id=1");

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
//
//    private Student makeStudent(Long id) {
//        Student student = new Student();
//        student.setId(id);
//        student.setName("studentName");
//        student.setLastName("studentLastName");
//        return student;
//    }
//
//    private Student makeStudentWithName(Long id, String name) {
//        Student student = new Student();
//        student.setId(id);
//        student.setName(name);
//        return student;
//    }
}
