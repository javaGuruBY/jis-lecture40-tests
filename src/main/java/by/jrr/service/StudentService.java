package by.jrr.service;

import by.jrr.bean.Student;
import by.jrr.repository.StudentRepository;
import lombok.NoArgsConstructor;
import org.assertj.core.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    Clock clock = Clock.systemDefaultZone();

    @VisibleForTesting
    public void setClock(Clock clock) {
        this.clock = clock;
    }

    @VisibleForTesting
    public void setDefaultClock() {
        this.clock = Clock.systemDefaultZone();
    }

    @VisibleForTesting
    public void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void saveNewStudent(Student student) {
        student.setRegistrationStamp(LocalDateTime.now(clock));
        studentRepository.save(student);
    }

    public Student findStudentById(Long id) {
        return studentRepository.findById(id).orElseGet(Student::new);
    }

    public Student updateStudent(Student student) {
        return studentRepository.updateStudent(student);
    }


    public void deleteStudent(Student student) {
        studentRepository.delete(student.getId());
    }

    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    public List<Student> findAllStudentsRegisteredYesterday() {
        LocalDate today = LocalDate.now(clock);
        LocalDate yesterday = today.minusDays(1);
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .filter(s -> s.getRegistrationStamp() != null)
                .filter(s -> s.getRegistrationStamp().isAfter(yesterday.atStartOfDay()))
                .collect(Collectors.toList());
    }
}
