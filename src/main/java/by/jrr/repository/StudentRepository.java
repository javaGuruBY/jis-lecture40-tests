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

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Student student) {
        String query = String.format("INSERT INTO student values (%s, '%s', '%s', '%s')",
                null,
                student.getName(),
                student.getLastName(),
                student.getRegistrationStamp().toString());
        jdbcTemplate.execute(query);
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

    public Student updateStudent(Student student) {
        return null;
    }

    public Student delete(Long id) {
        return null;
    }
}
