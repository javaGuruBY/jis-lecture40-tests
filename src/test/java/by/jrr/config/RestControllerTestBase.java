package by.jrr.config;

import by.jrr.controller.ApiStudentController;
import by.jrr.repository.StudentRepository;
import by.jrr.service.StudentService;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        SpringWebAppInitializer.class,
        WebConfig.class,
        StudentRepository.class,
        StudentService.class,
        ApiStudentController.class,
        DataBaseConfig.class,
        TestDBConfig.class
})
@WebAppConfiguration
public class RestControllerTestBase {
}
