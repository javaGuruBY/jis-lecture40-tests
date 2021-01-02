package by.jrr.repository;

import by.jrr.config.DataBaseConfig;
import by.jrr.config.SpringWebAppInitializer;
import by.jrr.config.TestDBConfig;
import by.jrr.config.WebConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

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
@ActiveProfiles("test2")
public class StudentRepositoryTest {

    @Autowired
    StudentRepository studentRepository;

    @Test

    public void findAll() {
        System.out.println(studentRepository.findAll().size());
    }
}
