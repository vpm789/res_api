package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findStudentByAge(int age);
    Collection<Student> findStudentByAgeBetween(int minAge, int maxAge);
    Collection<Student> findStudentByFaculty(Faculty faculty);
}
