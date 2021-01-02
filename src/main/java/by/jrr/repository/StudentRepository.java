package by.jrr.repository;

import by.jrr.bean.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class StudentRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void save(Student student) {

    }

    public Optional<Student> findById(Long id) {
        String query = "SELECT * FROM student WHERE id=" + id;
        List<Student> result = jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Student.class));
        if (!result.isEmpty()) {
            return Optional.of(result.get(0));
        }
        return Optional.empty();
    }

    public List<Student> findAll() {
        String query = "SELECT * FROM student";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Student.class));
    }
}
