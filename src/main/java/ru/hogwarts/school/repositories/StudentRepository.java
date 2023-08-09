package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findStudentByAge(int age);
    Collection<Student> findStudentByAgeBetween(int minAge, int maxAge);
    Collection<Student> findStudentByFaculty(Faculty faculty);
    @Query(value = "SELECT COUNT(*) FROM student", nativeQuery = true)
    Integer getCountStudents();

    @Query(value = "SELECT AVG(age) FROM student", nativeQuery = true)
    Integer getAvgAge();

    @Query(value = "SELECT * FROM student OFFSET (SELECT COUNT(*) FROM student)-5", nativeQuery = true)
    List<Student> getLastStudents();
}
